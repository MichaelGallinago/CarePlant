package net.micg.plantcare.presentation.article

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.BuildConfig
import net.micg.plantcare.R
import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.databinding.FragmentArticleBinding
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.di.viewModel.ViewModelFactory
import net.micg.plantcare.presentation.models.JsBridge
import net.micg.plantcare.utils.FirebaseUtils
import net.micg.plantcare.utils.FirebaseUtils.ARTICLE_READ_DURATION
import net.micg.plantcare.utils.InsetsUtils
import javax.inject.Inject
import kotlin.getValue
import androidx.core.view.isVisible
import net.micg.plantcare.utils.ErrorMessageUtils

class ArticleFragment : Fragment(R.layout.fragment_article) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentArticleBinding by viewBinding()
    private val viewModel: ArticleViewModel by viewModels { factory }

    private var startTime = 0L
    private var articleName = ""

    private var isSectionsClosed = true

    private var fabAnimator: ValueAnimator? = null

    private var isFragmentOpened = false

    private val fabPulseInterval = 10_000L
    private val handler = Handler(Looper.getMainLooper())
    private val fabPulseRunnable = object : Runnable {
        override fun run() {
            pulseFabColor()
            handler.postDelayed(this, fabPulseInterval)
        }
    }

    private fun pulseFabColor() {
        val colorFrom = requireContext().getColor(R.color.fab)
        val colorTo = requireContext().getColor(R.color.fab_highlight)

        fabAnimator?.cancel()

        fabAnimator = ValueAnimator.ofArgb(colorFrom, colorTo, colorFrom).apply {
            duration = 1000
            addUpdateListener { animator ->
                binding.createAlarmButton.backgroundTintList =
                    ColorStateList.valueOf(animator.animatedValue as Int)
            }
            start()
        }
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pulseFabColor()

        viewModel.alarmCreationModule.observe(viewLifecycleOwner) { data ->
            setUpCreateAlarmButton(data)
        }

        setUpWebView()
        setUpArguments()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() = with(binding.webView) {
        InsetsUtils.addTopInsetsMarginToCurrentView(binding.webView)
        settings.javaScriptEnabled = true

        addJavascriptInterface(JsBridge(this@ArticleFragment), "AndroidBridge")

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                evaluateJavascript(
                    """
                (function() {
                    const detailsList = document.querySelectorAll("details");

                    function notifyState() {
                        const allClosed = Array.from(detailsList).every(d => !d.open);
                        AndroidBridge.onSectionsStateChanged(allClosed);
                    }

                    detailsList.forEach(details => {
                        details.addEventListener("toggle", notifyState);
                    });

                    notifyState();
                })();
                """.trimIndent(), null
                )
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)

                if (!request.isForMainFrame) return

                val context = requireContext()
                val message =
                    ErrorMessageUtils.getMessage(context, ErrorMessageUtils.Type.LoadingError)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                view.visibility = View.GONE
            }
        }
    }

    fun onSectionStateChanged(isAllClosed: Boolean) {
        isSectionsClosed = isAllClosed
        updateHelpVisibility()
    }

    override fun onResume() {
        super.onResume()
        isFragmentOpened = true
        startTime = System.currentTimeMillis()
        handler.postDelayed(fabPulseRunnable, fabPulseInterval)
    }

    override fun onPause() {
        super.onPause()
        isFragmentOpened = false
        handler.removeCallbacks(fabPulseRunnable)

        FirebaseUtils.logEvent(requireContext(), ARTICLE_READ_DURATION, Bundle().apply {
            putLong("duration_ms", System.currentTimeMillis() - startTime)
            putString("article_name", articleName)
        })
    }

    private fun setUpCreateAlarmButton(data: AlarmCreationModel) = with(binding.createAlarmButton) {
        visibility = View.VISIBLE
        updateHelpVisibility()
        setOnClickListener {
            with(data) {
                findNavController().navigate(
                    ArticleFragmentDirections.actionArticleFragmentToAlarmCreationFragment(
                        plantName = plantName,
                        interval = interval,
                        fragmentName = "Article",
                        isEdition = false,
                        fertilizingInterval = fertilizingInterval,
                        waterSprayingInterval = waterSprayingInterval
                    )
                )
            }
        }
    }

    private fun setUpArguments() = arguments?.let {
        ArticleFragmentArgs.fromBundle(it).articleName
    }?.let { name ->
        articleName = name
        context?.let { ctx ->
            val locale = ctx.getString(R.string.culture_name)
            viewModel.getAlarmCreationData(locale, "/$name.json")
            with(binding.webView) {
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl("$ARTICLE_FOLDER/$locale/$name.html")
            }

            FirebaseUtils.logEvent(ctx, FirebaseUtils.ARTICLE_ENTERS, Bundle().apply {
                putString("name", name)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fabAnimator?.cancel()
        fabAnimator = null
    }

    private fun updateHelpVisibility() {
        if (!isFragmentOpened) return
        activity?.runOnUiThread {
            with(binding) {
                val visibility = if (isSectionsClosed && createAlarmButton.isVisible)
                    View.VISIBLE else View.INVISIBLE
                helpLabel.visibility = visibility
                arrow.visibility = visibility
            }
        }
    }

    companion object {
        private const val ARTICLE_FOLDER = "${BuildConfig.WEB_STORAGE_URL}data/articles/"
    }
}
