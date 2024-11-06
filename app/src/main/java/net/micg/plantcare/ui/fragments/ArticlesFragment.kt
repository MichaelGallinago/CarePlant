package net.micg.plantcare.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentArticlesBinding
import net.micg.plantcare.ui.adapters.ArticlesAdapter

class ArticlesFragment : Fragment(R.layout.fragment_alarms) {
    private val binding: FragmentArticlesBinding by viewBinding()
    private val articlesAdapter = ArticlesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
        }

        //articlesAdapter.submitValue(getScheduleForThisDay())
    }
}
