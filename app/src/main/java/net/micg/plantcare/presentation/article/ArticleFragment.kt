package net.micg.plantcare.presentation.article

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.BuildConfig
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val binding: FragmentArticleBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        binding.backButton.setOnClickListener {
            navController.popBackStack()
        }

        arguments?.let {
            ArticleFragmentArgs.fromBundle(it).articleUrl
        }?.let { url ->
            with(binding.webView) {
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl(BuildConfig.BASE_URL + url)
            }
        }
    }
}
