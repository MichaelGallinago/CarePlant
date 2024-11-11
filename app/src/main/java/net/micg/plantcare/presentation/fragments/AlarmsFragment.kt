package net.micg.plantcare.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmsBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.presentation.AlarmViewModel
import net.micg.plantcare.presentation.adapters.AlarmsAdapter
import javax.inject.Inject

class AlarmsFragment : Fragment(R.layout.fragment_alarms) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmsBinding by viewBinding()
    private val viewModel: AlarmViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alarmAdapter = AlarmsAdapter(
            onAlarmClick = { alarm ->
                // TODO: Обработка нажатия на элемент будильника
            },
            onToggleClick = { alarm, isEnabled ->
                viewModel.update(alarm.copy(isEnabled = isEnabled))
            }
        )

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alarmAdapter
        }

        viewModel.allAlarms.observe(viewLifecycleOwner) { alarms ->
            alarmAdapter.submitList(alarms)
        }

        viewModel.updateAlarms()

        val navController = findNavController()
        binding.addAlarmButton.setOnClickListener {
            navController.navigate(R.id.alarmCreationFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.alarmsFragment, inclusive = false)
                    .build())
        }
    }
}
