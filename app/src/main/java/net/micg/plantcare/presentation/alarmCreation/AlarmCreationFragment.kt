package net.micg.plantcare.presentation.alarmCreation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmCreationBinding
import net.micg.plantcare.di.appComponent
import net.micg.plantcare.di.viewModel.ViewModelFactory
import net.micg.plantcare.utils.AlarmCreationUtils
import net.micg.plantcare.utils.AlarmCreationUtils.calculateIntervalInMillis
import net.micg.plantcare.utils.AlarmCreationUtils.getCurrentCalendar
import net.micg.plantcare.utils.AlarmCreationUtils.getTypeName
import net.micg.plantcare.utils.FirebaseUtils
import net.micg.plantcare.utils.FirebaseUtils.ALARM_CREATION_ABANDONED
import net.micg.plantcare.utils.InsetsUtils.addTopInsetsMarginToCurrentView
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Calendar.getInstance
import java.util.UUID
import javax.inject.Inject

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmCreationViewModel by viewModels { factory }

    private var isEditing = false
    private var editingId = 0L
    private var editingIsEnabled = true

    private var wasAlarmSaved = false

    private var wasDateSelected = false
    private var wasTimeSelected = false
    private var wasIntervalEdited = false

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpEdgeToEdgeForCurrentFragment()
        setUpListeners(findNavController())
        setUpFragment()
        setUpArguments()
        setUpRadioGroupListener()
    }

    private fun setUpRadioGroupListener() = with(binding.actionRadioGroup) {
        setOnCheckedChangeListener { _, checkedId ->
            val isTransplanting = checkedId == R.id.radioTransplanting
            val visibility = if (isTransplanting) View.GONE else View.VISIBLE

            with(binding) {
                intervalLabel.visibility = visibility
                intervalValue.visibility = visibility
                removeButton.visibility = visibility
                addButton.visibility = visibility
            }
        }
    }

    private fun setUpArguments() = arguments?.let {
        AlarmCreationFragmentArgs.fromBundle(it)
    }?.let { args ->
        with(args) {
            updateInterval(interval.toLong().coerceIn(INTERVAL_MIN.toLong(), INTERVAL_MAX.toLong()))
            binding.nameEditText.setText(plantName)

            isEditing = isEdition
            editingId = id
            editingIsEnabled = isEnabled

            context?.let { ctx ->
                FirebaseUtils.logEvent(ctx, FirebaseUtils.ALARM_CREATION_ENTERS, Bundle().apply {
                    putString("from_screen", fragmentName)
                })
            }
        }
    }

    private fun setUpEdgeToEdgeForCurrentFragment() {
        addTopInsetsMarginToCurrentView(binding.confirmButton)
        addTopInsetsMarginToCurrentView(binding.cancelButton)
    }

    private fun setUpFragment() = with(binding) {
        intervalValue.addTextChangedListener(IntervalValueTextWatcher())

        removeButton.setOnClickListener {
            if (viewModel.interval > INTERVAL_MIN) {
                updateInterval(viewModel.interval - 1)
            }
        }

        addButton.setOnClickListener {
            if (viewModel.interval < INTERVAL_MAX) {
                updateInterval(viewModel.interval + 1)
            }
        }

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
            navController.popBackStack()
        }

        confirmButton.setOnClickListener {
            saveAlarm()
            navController.navigate(
                AlarmCreationFragmentDirections.actionAlarmCreationFragmentToAlarmsFragment()
            )
        }
    }

    private fun pickDate() = with(getCurrentCalendar()) {
        DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            ::setDate,
            get(YEAR),
            get(MONTH),
            get(DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = timeInMillis
            show()
        }
    }

    private fun setDate(
        picker: DatePicker, year: Int, month: Int, day: Int
    ) = with(viewModel.timeStorage) {
        this.year = year
        this.month = month
        dayOfMonth = day
        binding.dateSelector.text = dateFormated
        wasDateSelected = true
    }

    private fun pickTime() = with(getCurrentCalendar()) {
        TimePickerDialog(
            requireContext(),
            R.style.CustomTimePickerDialog,
            ::setTime,
            get(HOUR_OF_DAY),
            get(MINUTE),
            true
        ).show()
    }

    private fun setTime(picker: TimePicker, hour: Int, minute: Int) = with(viewModel.timeStorage) {
        hourOfDay = hour
        this.minute = minute
        binding.timeSelector.text = timeFormated
        wasTimeSelected = true
    }

    private fun saveAlarm() = with(binding) {
        wasAlarmSaved = true

        val name = nameEditText.text.toString()
        var interval = viewModel.interval
        val type = when {
            radioWatering.isChecked -> 0
            radioFertilizing.isChecked -> 1
            radioTransplanting.isChecked -> {
                interval = 0
                2
            }
            else -> 0
        }

        val context = requireContext()
        if (isEditing) {
            viewModel.updateData(
                editingId,
                name,
                type.toByte(),
                dateInMillis,
                calculateIntervalInMillis(interval),
                editingIsEnabled
            )

            FirebaseUtils.logEvent(context, FirebaseUtils.EDITED_NOTIFICATIONS, Bundle().apply {
                putString("type", getTypeName(type))
                putString("name", name)
                putString("date", AlarmCreationUtils.convertTimeToString(dateInMillis))
                putLong("interval", interval)
            })
        } else {
            sendMessageOnFirst(context)

            viewModel.insert(
                name,
                type.toByte(),
                dateInMillis,
                calculateIntervalInMillis(interval)
            )

            FirebaseUtils.logEvent(context, FirebaseUtils.CREATED_NOTIFICATIONS, Bundle().apply {
                putString("type", getTypeName(type))
                putString("name", name)
                putString("date", AlarmCreationUtils.convertTimeToString(dateInMillis))
                putLong("interval", interval)
            })
        }
    }

    fun sendMessageOnFirst(context: Context) {
        val prefs = context.getSharedPreferences("device_prefs", MODE_PRIVATE)
        var isFirstAlarmCreation = prefs.getBoolean("is_first_alarm_creation", true)

        if (!isFirstAlarmCreation) return

        prefs.edit { putBoolean("is_first_alarm_creation", false) }

        val message = context.getString(R.string.first_alarm_creation)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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

    private inner class IntervalValueTextWatcher : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (viewModel.isUpdating) return

            val value = s.toString().toIntOrNull()
            if (value != null && value in INTERVAL_MIN..INTERVAL_MAX) {
                viewModel.isUpdating = true
                viewModel.interval = value.toLong()
                wasIntervalEdited = true
                viewModel.isUpdating = false
            }
        }
    }

    private fun updateInterval(newInterval: Long) = with(binding) {
        wasIntervalEdited = true
        viewModel.isUpdating = true
        viewModel.interval = newInterval
        val intervalText = newInterval.toString()
        intervalValue.setText(intervalText)
        intervalValue.setSelection(intervalText.length)
        viewModel.isUpdating = false
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (wasAlarmSaved || isEditing) return

        with(binding) {
            val hasName = nameEditText.text.toString().isNotBlank()
            val type = when {
                radioWatering.isChecked -> "watering"
                radioFertilizing.isChecked -> "fertilizing"
                radioTransplanting.isChecked -> "transplanting"
                else -> "none"
            }

            FirebaseUtils.logEvent(requireContext(), ALARM_CREATION_ABANDONED, Bundle().apply {
                putBoolean("entered_name", hasName)
                putBoolean("selected_date", wasDateSelected)
                putBoolean("selected_time", wasTimeSelected)
                putBoolean("set_interval", wasIntervalEdited)
                putString("selected_type", type)
            })
        }
    }

    companion object {
        private const val INTERVAL_MIN = 1
        private const val INTERVAL_MAX = 365
    }
}
