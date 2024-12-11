package net.micg.plantcare.presentation.articles

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentArticlesBinding
import net.micg.plantcare.di.viewModel.ViewModelFactory
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.presentation.utils.InsetsUtils.addTopInsetsPaddingToCurrentView
import javax.inject.Inject

class ArticlesFragment : Fragment(R.layout.fragment_articles) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentArticlesBinding by viewBinding()
    private val viewModel: ArticlesViewModel by viewModels { factory }

    private val articlesAdapter = ArticlesAdapter { article ->
        findNavController().navigate(
            ArticlesFragmentDirections.Companion.actionArticlesFragmentToArticleFragment(
                article.name
            )
        )
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEdgeToEdgeForCurrentFragment()
        setUpRecyclerView()
        setUpViewModel()
        setUpSearchView()
    }

    override fun onDestroyView() {
        viewModel.onDestroyArticlesFragment()
        super.onDestroyView()
    }

    private fun setUpEdgeToEdgeForCurrentFragment() =
        addTopInsetsPaddingToCurrentView(binding.recycler)

    private fun setUpViewModel() = with(viewModel) {
        errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }

        articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.submitList(articles)
        }

        loadArticles()
    }

    private fun setUpRecyclerView() = with(binding.recycler) {
        layoutManager = LinearLayoutManager(context)
        adapter = articlesAdapter

        ContextCompat.getDrawable(context, R.drawable.divider_shape)?.also {
            with(DividerItemDecoration(context, DividerItemDecoration.VERTICAL)) {
                setDrawable(it)
                addItemDecoration(this)
            }
        }
    }

    private fun setUpSearchView() = with(binding.searchView) {
        setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus && isIconified) {
                isIconified = false
            }
        }

        setOnClickListener {
            isIconified = false
            true
        }

        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.filter = it }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filter = newText.orEmpty()
                return true
            }
        })
    }
}
