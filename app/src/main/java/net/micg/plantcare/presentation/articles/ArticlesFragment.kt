package net.micg.plantcare.presentation.articles

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentArticlesBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
import javax.inject.Inject

class ArticlesFragment : Fragment(R.layout.fragment_articles) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentArticlesBinding by viewBinding()
    private val viewModel: ArticlesViewModel by viewModels { factory }
    private val articlesAdapter = ArticlesAdapter { article ->
        val action = ArticlesFragmentDirections.Companion.actionArticlesFragmentToArticleFragment(article.url)
        findNavController().navigate(action)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.submitList(articles)
        }

        viewModel.loadArticles()

        with(binding.recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }
    }
}
