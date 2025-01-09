package net.micg.plantcare.presentation.article

import android.content.Context
import android.os.Bundle
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
import net.micg.plantcare.presentation.article.ArticleFragmentDirections.Companion.actionArticleFragmentToAlarmCreationFragment
import javax.inject.Inject
import kotlin.getValue

class ArticleFragment : Fragment(R.layout.fragment_article) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentArticleBinding by viewBinding()
    private val viewModel: ArticleViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.alarmCreationModule.observe(viewLifecycleOwner) { data ->
            setUpCreateAlarmButton(data)
        }

        setUpArguments()
    }

    private fun setUpCreateAlarmButton(data: AlarmCreationModel) = with(binding.createAlarmButton) {
        visibility = View.VISIBLE
        setOnClickListener {
            with(data) {
                findNavController().navigate(
                    actionArticleFragmentToAlarmCreationFragment(
                        plantName, interval
                    )
                )
            }
        }
    }

    private fun setUpArguments() = arguments?.let {
        ArticleFragmentArgs.fromBundle(it).articleName
    }?.let { name ->
        viewModel.getAlarmCreationData("$name.json")
        with(binding.webView) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            loadUrl("$ARTICLE_FOLDER$name.html")
        }
    }

    companion object {
        private const val ARTICLE_FOLDER = "${BuildConfig.BASE_URL}articles/"
    }
}
