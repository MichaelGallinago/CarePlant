package net.micg.plantcare.presentation.alarms

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
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
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmViewModel by viewModels { factory }

    private var selectedTime: Long = getCurrentCalendar().run {
        set(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        timeInMillis
    }

    private var selectedDate: Long = getCurrentCalendar().run {
        set(0, 0, 0, get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), 0)
        timeInMillis
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners(findNavController())

        with(binding) {
            setupSpinner(timeHoursSpinner, 24)
            setupSpinner(timeMinutesSpinner, 60)
            setupSpinner(timeDaysSpinner, 365)

            with(getCurrentCalendar()) {
                dateSelector.text = getDateFormated(
                    get(Calendar.YEAR),
                    get(Calendar.MONTH),
                    get(Calendar.DAY_OF_MONTH))
                timeSelector.text = getTimeFormated(
                    get(Calendar.HOUR_OF_DAY),
                    get(Calendar.MINUTE))
            }

            timeSelector.setOnClickListener { pickTime() }
            dateSelector.setOnClickListener { pickDate() }
        }
    }

    private fun pickDate() {
        val calendar = getCurrentCalendar()

        DatePickerDialog(
            requireContext(),
            ::setDate,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = calendar.timeInMillis
            show()
        }
    }

    private fun setDate(picker: DatePicker, year: Int, month: Int, day: Int) {
        binding.dateSelector.text = getDateFormated(year, month, day)

        getCurrentCalendar().apply {
            set(year, month, day, 0, 0, 0)
            selectedDate = timeInMillis
        }
    }

    private fun pickTime() {
        val calendar = getCurrentCalendar()

        TimePickerDialog(
            requireContext(),
            ::setTime,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun setTime(picker: TimePicker, hour: Int, minute: Int) {
        binding.timeSelector.text = getTimeFormated(hour, minute)

        getCurrentCalendar().apply {
            set(0, 0, 0, hour, minute, 0)
            selectedTime = timeInMillis
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
        with(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                List(size) { it.toString() })
        ) {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = this
        }
    }

    private fun saveAlarm(): Boolean {
        val name = binding.nameEditText.text.toString()
        val type = if (binding.radioWatering.isChecked) 0.toByte() else 1.toByte()
        val days = binding.timeDaysSpinner.selectedItem.toString().toLong()
        val hours = binding.timeHoursSpinner.selectedItem.toString().toLong()
        val minutes = binding.timeMinutesSpinner.selectedItem.toString().toLong()

        viewModel.insert(Alarm(
            name = name,
            type = type,
            dateInMillis = selectedDate + selectedTime,
            intervalInMillis = ((days * 24L + hours) * 60L + minutes) * 60L * 1000L,
            isEnabled = true
        ))
        return true
    }

    companion object {
        private fun getCurrentCalendar() = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }

        private fun getDateFormated(year: Int, month: Int, day: Int) =
            "%02d/%02d/%04d".format(day, month + 1, year)

        private fun getTimeFormated(hour: Int, minute: Int) =
            "%02d:%02d".format(hour, minute)
    }
}