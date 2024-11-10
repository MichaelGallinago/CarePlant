package net.micg.plantcare.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmsBinding
import net.micg.plantcare.presentation.AlarmViewModel
import net.micg.plantcare.presentation.adapters.AlarmsAdapter

class AlarmsFragment : Fragment(R.layout.fragment_alarms) {
    private val binding: FragmentAlarmsBinding by viewBinding()
    private val alarmViewModel: AlarmViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alarmAdapter = AlarmsAdapter(
            onAlarmClick = { alarm ->
                // TODO: Обработка нажатия на элемент будильника
            },
            onToggleClick = { alarm, isEnabled ->
                alarmViewModel.update(alarm.copy(isEnabled = !isEnabled))
            }
        )

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alarmAdapter
        }

        alarmViewModel.allAlarms.observe(viewLifecycleOwner) { alarms ->
            alarmAdapter.submitList(alarms)
        }
    }
}
