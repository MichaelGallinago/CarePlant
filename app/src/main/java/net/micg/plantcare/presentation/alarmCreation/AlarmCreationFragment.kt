package net.micg.plantcare.presentation.alarmCreation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
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
import net.micg.plantcare.utils.AlarmHelper
import net.micg.plantcare.utils.CalendarPermissionHelper
import net.micg.plantcare.utils.DateTimePickerHelper
import net.micg.plantcare.utils.FirebaseUtils
import net.micg.plantcare.utils.FirebaseUtils.ALARM_CREATION_ABANDONED
import net.micg.plantcare.utils.FirebaseUtils.logEvent
import net.micg.plantcare.utils.InsetsUtils.addBottomInsetsMarginToCurrentView
import net.micg.plantcare.utils.InsetsUtils.addTopInsetsMarginToCurrentView
import net.micg.plantcare.utils.IntervalUtils
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Calendar.getInstance
import javax.inject.Inject
import kotlin.math.max

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val binding: FragmentAlarmCreationBinding by viewBinding()
    private val viewModel: AlarmCreationViewModel by viewModels { factory }

    private var wateringInterval = 1
    private var fertilizingInterval = 1
    private var waterSprayingInterval = 1

    private var wasDateSelected = false
    private var wasTimeSelected = false
    private var wasIntervalEdited = false

    private var isCalendarPermissionGranted = false

    private lateinit var alarmHelper: AlarmHelper

    private val dateInMillis get() = getInstance().apply {
        timeInMillis = 0
        with(viewModel.timeStorage) {
            set(YEAR, year)
            set(MONTH, month)
            set(DAY_OF_MONTH, dayOfMonth)
            set(HOUR_OF_DAY, hourOfDay)
            set(MINUTE, minute)
        }
    }.timeInMillis

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEdgeToEdge()
        initPermissionSwitch()
        initIntervalField()
        handleArgs()
        initRadioGroup()
        setUpListeners(findNavController())
    }

    private fun setUpListeners(navController: NavController) = with(binding) {
        cancelButton.setOnClickListener { navController.popBackStack() }
        confirmButton.setOnClickListener { saveAndExit(navController) }
    }

    private fun initEdgeToEdge() = with(binding) {
        addTopInsetsMarginToCurrentView(confirmButton)
        addTopInsetsMarginToCurrentView(cancelButton)
        addBottomInsetsMarginToCurrentView(switchCalendarButton)
    }

    private fun initPermissionSwitch() = CalendarPermissionHelper.bind(
        fragment = this,
        switch = binding.switchCalendarButton,
        onGranted = { isCalendarPermissionGranted = true }
    )

    private fun initIntervalField() = with(binding) {
        intervalValue.addTextChangedListener(
            IntervalUtils.newWatcher(viewModel) { new ->
                updateInterval(new)
            }
        )
        removeButton.setOnClickListener { updateInterval(viewModel.interval - 1) }
        addButton.setOnClickListener { updateInterval(viewModel.interval + 1) }

        with(viewModel.timeStorage) {
            with(AlarmCreationUtils.getCurrentCalendar()) {
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

    private fun initRadioGroup() = with(binding.actionRadioGroup) {
        setOnCheckedChangeListener { _, checked ->
            val isTransplanting = checked == R.id.radioTransplanting
            listOf(binding.intervalLabel, binding.intervalValue,
                binding.removeButton, binding.addButton
            ).forEach { it.visibility = if (isTransplanting) View.GONE else View.VISIBLE }

            when (checked) {
                R.id.radioWatering      -> updateInterval(wateringInterval)
                R.id.radioWaterSpraying -> updateInterval(waterSprayingInterval)
                R.id.radioFertilizing   -> updateInterval(fertilizingInterval)
            }
        }
    }

    private fun handleArgs() = arguments?.let(AlarmCreationFragmentArgs::fromBundle)?.apply {
        alarmHelper = AlarmHelper(requireContext(), viewModel)

        binding.radioWaterSpraying.visibility =
            if (waterSprayingInterval > 0) View.VISIBLE else View.GONE

        wateringInterval = max(interval, 1)
        this@AlarmCreationFragment.fertilizingInterval = max(fertilizingInterval, 1)
        this@AlarmCreationFragment.waterSprayingInterval  = max(waterSprayingInterval, 1)

        updateInterval(interval)
        binding.nameEditText.setText(plantName)

        if (isEdition) {
            alarmHelper.setEditing(id, isEnabled)
        }

        logEvent(requireContext(), FirebaseUtils.ALARM_CREATION_ENTERS, Bundle().apply {
            putString("from_screen", fragmentName)
        })
    }

    private fun pickDate() = DateTimePickerHelper.showDate(
        fragment = this,
        calendar = getCurrentCalendar(),
        themeRes = R.style.CustomDatePickerDialog
    ) { y, m, d ->
        wasDateSelected = true
        with(viewModel.timeStorage) {
            year = y
            month = m
            dayOfMonth = d
            binding.dateSelector.text = dateFormated
        }
    }

    private fun pickTime() = DateTimePickerHelper.showTime(
        fragment = this,
        calendar = getCurrentCalendar(),
        themeRes = R.style.CustomTimePickerDialog
    ) { h, m ->
        wasTimeSelected = true
        with(viewModel.timeStorage) {
            hourOfDay = h
            minute = m
            binding.timeSelector.text = timeFormated
        }
    }

    private fun updateInterval(raw: Int) = updateInterval(raw.toLong())

    private fun updateInterval(raw: Long) = with(binding) {
        wasIntervalEdited = true

        val value = raw.coerceIn(IntervalUtils.MIN.toLong(), IntervalUtils.MAX.toLong())
        viewModel.isUpdating = true
        viewModel.interval = value
        with(intervalValue) {
            setText(value.toString())
            setSelection(text.length)
        }
        viewModel.isUpdating = false

        when {
            radioWatering.isChecked      -> wateringInterval      = value.toInt()
            radioFertilizing.isChecked   -> fertilizingInterval   = value.toInt()
            radioWaterSpraying.isChecked -> waterSprayingInterval = value.toInt()
        }
    }

    private fun saveAndExit(navController: NavController) {
        saveAlarm()
        navController.navigate(
            AlarmCreationFragmentDirections.actionAlarmCreationFragmentToAlarmsFragment()
        )
    }

    private fun saveAlarm() = with(binding) {
        if (!alarmHelper.isEditing)
            sendMessageOnFirst(requireContext())

        val name = nameEditText.text.toString()
        val (type, interval) = extractTypeAndInterval()
        val isInCalendar = isCalendarPermissionGranted && switchCalendarButton.isChecked
        alarmHelper.save(name, type, dateInMillis, interval, isInCalendar)
    }

    private fun extractTypeAndInterval() = when {
        binding.radioWatering.isChecked -> 0
        binding.radioFertilizing.isChecked -> 1
        binding.radioTransplanting.isChecked -> 2
        binding.radioWaterSpraying.isChecked -> 3
        else -> 0
    }.run {
        toByte() to (if (this == 2) 0 else viewModel.interval.toInt())
    }

    private fun getCurrentCalendar() = getInstance()

    private fun sendMessageOnFirst(ctx: Context) {
        ctx.getSharedPreferences("device_prefs", MODE_PRIVATE).apply {
            if (getBoolean("is_first_alarm_creation", true)) {
                edit { putBoolean("is_first_alarm_creation", false) }
                Toast.makeText(ctx, R.string.first_alarm_creation, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (alarmHelper.wasAlarmSaved || alarmHelper.isEditing) return
        abandonAlarmCreation()
    }

    private fun abandonAlarmCreation() = with(binding) {
        logEvent(requireContext(), ALARM_CREATION_ABANDONED, Bundle().apply {
            putBoolean("entered_name", nameEditText.text.toString().isNotBlank())
            putBoolean("selected_date", wasDateSelected)
            putBoolean("selected_time", wasTimeSelected)
            putBoolean("set_interval", wasIntervalEdited)
            putString("selected_type", when {
                radioWatering.isChecked -> "watering"
                radioFertilizing.isChecked -> "fertilizing"
                radioTransplanting.isChecked -> "transplanting"
                radioWaterSpraying.isChecked -> "water_spraying"
                else -> "none"
            })
        })
    }
}
