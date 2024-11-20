package net.micg.plantcare.presentation.alarms

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
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
import java.util.Calendar
import javax.inject.Inject

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmViewModel by viewModels { factory }

    private var year: Int = 0
    private var month: Int = 0
    private var dayOfMonth: Int = 0
    private var hourOfDay: Int = 0
    private var minute: Int = 0

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
                year = get(Calendar.YEAR)
                month = get(Calendar.MONTH)
                dayOfMonth = get(Calendar.DAY_OF_MONTH)
                hourOfDay = get(Calendar.HOUR_OF_DAY)
                minute = get(Calendar.MINUTE)
            }

            with(dateSelector) {
                text = getDateFormated(year, month, dayOfMonth)
                setOnClickListener { pickDate() }
            }

            with(timeSelector) {
                text = getTimeFormated(hourOfDay, minute)
                setOnClickListener { pickTime() }
            }
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

        this.year = year
        this.month = month
        dayOfMonth = day
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

        hourOfDay = hour
        this.minute = minute
    }

    private fun setupListeners(navController: NavController) {
        binding.cancelButton.setOnClickListener { navController.popBackStack() }

        binding.confirmButton.setOnClickListener {
            if (trySaveAlarm()) {
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

    private fun trySaveAlarm(): Boolean {
        val name = binding.nameEditText.text.toString()
        val type = if (binding.radioWatering.isChecked) 0.toByte() else 1.toByte()
        val intervalDays = binding.timeDaysSpinner.selectedItem.toString().toLong()
        val intervalHours = binding.timeHoursSpinner.selectedItem.toString().toLong()
        val intervalMinutes = binding.timeMinutesSpinner.selectedItem.toString().toLong()

        val dateInMillis = Calendar.getInstance().apply {
            timeInMillis = 0L
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }.timeInMillis

        viewModel.insert(Alarm(
            name = name,
            type = type,
            dateInMillis = dateInMillis,
            intervalInMillis =
                ((intervalDays * 24L + intervalHours) * 60L + intervalMinutes) * 60000L,
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
