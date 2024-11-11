package net.micg.plantcare.presentation.alarms

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.data.models.alarm.Alarm
import net.micg.plantcare.databinding.FragmentAlarmCreationBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
import javax.inject.Inject

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmViewModel by viewModels { factory }

    private lateinit var checkboxes: List<CheckBox>

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners(findNavController())

        setupSpinner(binding.timeHourSpinner, 24)
        setupSpinner(binding.timeMinuteSpinner, 60)

        checkboxes = with(binding) { listOf(
            checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday,
            checkboxFriday, checkboxSaturday, checkboxSunday)
        }
    }

    private fun setupListeners(navController: NavController) {
        binding.cancelButton.setOnClickListener { navController.popBackStack() }

        binding.confirmButton.setOnClickListener {
            if (saveAlarm()) {
                navController.popBackStack()
            }
        }
    }

    private fun setupSpinner(spinner: Spinner, size: Int) {
        with(ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            List(size) { it.toString() }
        )) {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = this
        }
    }

    private fun saveAlarm(): Boolean {
        val daysOfWeekFlags = getSelectedDaysFlags()

        if (daysOfWeekFlags == 0) {
            Toast.makeText(
                requireContext(),
                "Выберите хотя бы один день недели",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val name = binding.nameEditText.text.toString()
        val type = if (binding.radioWatering.isChecked) 0.toByte() else 1.toByte()
        val hour = binding.timeHourSpinner.selectedItem.toString().toInt()
        val minute = binding.timeMinuteSpinner.selectedItem.toString().toInt()

        viewModel.insert(Alarm(
            name = name,
            type = type,
            timeInMinutes = hour * 60 + minute,
            daysOfWeekFlags = daysOfWeekFlags,
            isEnabled = true
        ))
        return true
    }

    private fun getSelectedDaysFlags(): Int {
        var flags = 0
        for (i in 0..6) {
            if (!checkboxes[i].isChecked) continue
            flags = flags or (1 shl i)
        }
        return flags
    }
}