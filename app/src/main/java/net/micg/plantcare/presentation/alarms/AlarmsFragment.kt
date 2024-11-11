package net.micg.plantcare.presentation.alarms

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.data.models.alarm.Alarm
import net.micg.plantcare.databinding.FragmentAlarmsBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
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

        createItemTouchHelper(alarmAdapter).attachToRecyclerView(binding.recycler)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alarmAdapter
        }

        viewModel.allAlarms.observe(viewLifecycleOwner) { alarms ->
            alarmAdapter.submitList(alarms)
        }

        val navController = findNavController()
        binding.addAlarmButton.setOnClickListener {
            navController.navigate(R.id.alarmCreationFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.alarmsFragment, inclusive = false)
                    .build())
        }

        viewModel.updateAlarms()
    }

    private fun createItemTouchHelper(adapter: AlarmsAdapter): ItemTouchHelper {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, // Перемещение
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Удаление
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                adapter.moveItem(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.delete(adapter.currentList[position])
                adapter.removeItem(position)
            }
        }

        return ItemTouchHelper(itemTouchHelperCallback)
    }
}
