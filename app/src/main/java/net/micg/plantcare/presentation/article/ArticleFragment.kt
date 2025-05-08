package net.micg.plantcare.presentation.article

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebSettings
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
import net.micg.plantcare.utils.FirebaseUtils
import net.micg.plantcare.utils.FirebaseUtils.ARTICLE_READ_DURATION
import net.micg.plantcare.utils.InsetsUtils
import javax.inject.Inject
import kotlin.getValue

class ArticleFragment : Fragment(R.layout.fragment_article) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentArticleBinding by viewBinding()
    private val viewModel: ArticleViewModel by viewModels { factory }

    private var startTime = 0L
    private var articleName = ""

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

        ValueAnimator.ofArgb(colorFrom, colorTo, colorFrom).apply {
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

        setUpArguments()

        InsetsUtils.addTopInsetsMarginToCurrentView(binding.webView)
    }

    override fun onResume() {
        super.onResume()
        startTime = System.currentTimeMillis()
        handler.postDelayed(fabPulseRunnable, fabPulseInterval)
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(fabPulseRunnable)

        context?.let { ctx ->
            FirebaseUtils.logEvent(ctx, ARTICLE_READ_DURATION, Bundle().apply {
                putLong("duration_ms", System.currentTimeMillis() - startTime)
                putString("article_name", articleName)
            })
        }
    }

    private fun setUpCreateAlarmButton(data: AlarmCreationModel) = with(binding.createAlarmButton) {
        visibility = View.VISIBLE
        setOnClickListener {
            with(data) {
                findNavController().navigate(
                    ArticleFragmentDirections.actionArticleFragmentToAlarmCreationFragment(
                        plantName, interval, "Article", false
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

    companion object {
        private const val ARTICLE_FOLDER = "${BuildConfig.WEB_STORAGE_URL}data/articles/"
    }
}
