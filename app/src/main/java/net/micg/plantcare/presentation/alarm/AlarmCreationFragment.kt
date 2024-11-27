package net.micg.plantcare.presentation.alarm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.SeekBar
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmCreationBinding
import net.micg.plantcare.di.ViewModelFactory
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.presentation.utils.AlarmCreationUtils.calculateIntervalInMillis
import java.util.Calendar.*
import net.micg.plantcare.presentation.utils.AlarmCreationUtils.getCurrentCalendar
import net.micg.plantcare.presentation.utils.InsetsUtils.addTopInsetsMarginToCurrentView
import javax.inject.Inject

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmCreationViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEdgeToEdgeForCurrentFragment()
        setUpListeners(findNavController())
        setUpFragment()
    }

    private fun setUpEdgeToEdgeForCurrentFragment() {
        addTopInsetsMarginToCurrentView(binding.confirmButton)
        addTopInsetsMarginToCurrentView(binding.cancelButton)
    }

    private fun setUpFragment() = with(binding) {
        with(intervalBar) {
            setOnSeekBarChangeListener(IntervalBarChangeListener())
            max = 364
        }
        updateIntervalValue(0L)

        with(viewModel.timeStorage) {
            with(getCurrentCalendar()) {
                year = get(YEAR)
                month = get(MONTH)
                dayOfMonth = get(DAY_OF_MONTH)
                hourOfDay = get(HOUR_OF_DAY)
                minute = get(MINUTE)
            }

            with(dateSelector) {
                text = dateFormated
                setOnClickListener { pickDate() }
            }

            with(timeSelector) {
                text = timeFormated
                setOnClickListener { pickTime() }
            }
        }
    }

    private fun setUpListeners(navController: NavController) = with(binding) {
        cancelButton.setOnClickListener {
            if (viewModel.isCreationStarted) return@setOnClickListener
            navController.popBackStack()
        }

        viewModel.isCreationFinished.observe(viewLifecycleOwner) { isFinished ->
            if (!isFinished) return@observe
            navController.popBackStack()
        }

        confirmButton.setOnClickListener {
            viewModel.isCreationStarted = true
            saveAlarm()
        }
    }

    private fun pickDate() = with(getCurrentCalendar()) {
        DatePickerDialog(
            requireContext(), ::setDate, get(YEAR), get(MONTH), get(DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = timeInMillis
            show()
        }
    }

    private fun setDate(picker: DatePicker, year: Int, month: Int, day: Int) =
        with(viewModel.timeStorage) {
            this.year = year
            this.month = month
            dayOfMonth = day
            binding.dateSelector.text = dateFormated
        }

    private fun pickTime() = with(getCurrentCalendar()) {
        TimePickerDialog(requireContext(), ::setTime, get(HOUR_OF_DAY), get(MINUTE), true).show()
    }

    private fun setTime(picker: TimePicker, hour: Int, minute: Int) = with(viewModel.timeStorage) {
        hourOfDay = hour
        this.minute = minute
        binding.timeSelector.text = timeFormated
    }

    private fun saveAlarm() = with(binding) {
        viewModel.insert(
            nameEditText.text.toString(),
            if (radioWatering.isChecked) 0.toByte() else 1.toByte(),
            dateInMillis,
            calculateIntervalInMillis(viewModel.interval)
        )
    }

    private fun updateIntervalValue(value: Long) {
        viewModel.interval = value + 1L
        binding.intervalValue.text = "${viewModel.interval}"
    }

    private val dateInMillis
        get() = getInstance().apply {
            timeInMillis = 0L
            with(viewModel.timeStorage) {
                set(YEAR, year)
                set(MONTH, month)
                set(DAY_OF_MONTH, dayOfMonth)
                set(HOUR_OF_DAY, hourOfDay)
                set(MINUTE, minute)
            }
        }.timeInMillis

    private inner class IntervalBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) =
            updateIntervalValue(progress.toLong())

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}
