package net.micg.plantcare.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmsBinding
import net.micg.plantcare.ui.adapters.AlarmsAdapter

class AlarmsFragment : Fragment(R.layout.fragment_alarms) {
    private val binding: FragmentAlarmsBinding by viewBinding()
    private val alarmsAdapter = AlarmsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alarmsAdapter
        }

        //alarmsAdapter.submitValue(getScheduleForThisDay())
    }
}
