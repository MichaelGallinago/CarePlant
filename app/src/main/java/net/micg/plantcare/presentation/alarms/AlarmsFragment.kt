package net.micg.plantcare.presentation.alarms

import android.content.Context
import android.os.Bundle
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
import net.micg.plantcare.databinding.FragmentAlarmsBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.presentation.alarm.AlarmCreationViewModel
import javax.inject.Inject

class AlarmsFragment : Fragment(R.layout.fragment_alarms) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmsBinding by viewBinding()
    private val viewModel: AlarmsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshAlarms()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AlarmsAdapter(onToggleClick = { alarm, isEnabled ->
            viewModel.update(isEnabled, alarm)
        }).also {
            createItemTouchHelper(it).attachToRecyclerView(binding.recycler)

            binding.recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = it
            }

            viewModel.allAlarms.observe(viewLifecycleOwner) { alarms -> it.submitList(alarms) }
        }

        val navController = findNavController()
        binding.addAlarmButton.setOnClickListener {
            navController.navigate(
                R.id.alarmCreationFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.alarmsFragment, inclusive = false).build()
            )
        }

        viewModel.refreshAlarms()
    }

    private fun createItemTouchHelper(adapter: AlarmsAdapter) =
        ItemTouchHelper(TouchHelperCallback(adapter))

    private inner class TouchHelperCallback(val adapter: AlarmsAdapter) :
        ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = with(adapter) {
            with(viewHolder.adapterPosition) {
                viewModel.delete(currentList[this])
                removeItem(this)
            }
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ) = false

        @Deprecated(
            "This project doesn't provide for moving items as a useful or mandatory feature"
        )
        private fun moveItem(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) {
            adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
        }
    }
}
