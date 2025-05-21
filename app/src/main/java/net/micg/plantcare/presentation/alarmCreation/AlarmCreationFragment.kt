package net.micg.plantcare.presentation.alarmCreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import net.micg.plantcare.R
import net.micg.plantcare.databinding.FragmentAlarmCreationBinding
import net.micg.plantcare.di.viewModel.ViewModelFactory
import net.micg.plantcare.utils.CalendarPermissionHelper
import net.micg.plantcare.utils.DateTimePickerHelper
import net.micg.plantcare.utils.InsetsUtils.addBottomInsetsMarginToCurrentView
import net.micg.plantcare.utils.InsetsUtils.addTopInsetsMarginToCurrentView
import net.micg.plantcare.utils.IntervalUtils
import java.util.Calendar.getInstance
import javax.inject.Inject
import kotlin.math.max

class AlarmCreationFragment : Fragment(R.layout.fragment_alarm_creation) {

    @Inject lateinit var factory: ViewModelFactory
    private val binding by viewBinding(FragmentAlarmCreationBinding::bind)
    private val viewModel: AlarmCreationViewModel by viewModels { factory }

    private var wateringInterval = 1
    private var fertilizingInterval = 1
    private var waterSprayingInterval = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEdgeToEdge()
        initPermissionSwitch()
        initIntervalField()
        initButtons()
        handleArgs()
        initRadioGroup()
    }

    private fun initEdgeToEdge() = with(binding) {
        addTopInsetsMarginToCurrentView(confirmButton)
        addTopInsetsMarginToCurrentView(cancelButton)
        addBottomInsetsMarginToCurrentView(switchCalendarButton)
    }

    private fun initPermissionSwitch() = CalendarPermissionHelper.bind(
        fragment = this,
        switch = binding.switchCalendarButton,
        coroutineScope = viewLifecycleOwner.lifecycleScope,
        onGranted = { /* можно сразу создать событие, если нужно */ }
    )

    private fun initIntervalField() = with(binding) {
        intervalValue.addTextChangedListener(
            IntervalUtils.newWatcher(viewModel) { new ->
                updateInterval(new)
            }
        )
        removeButton.setOnClickListener { updateInterval(viewModel.interval - 1) }
        addButton.setOnClickListener { updateInterval(viewModel.interval + 1) }
    }

    private fun initButtons() = with(binding) {
        cancelButton.setOnClickListener { findNavController().popBackStack() }
        confirmButton.setOnClickListener { saveAlarmAndExit() }
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
        binding.radioWaterSpraying.visibility =
            if (waterSprayingInterval > 0) View.VISIBLE else View.GONE

        wateringInterval       = max(interval, 1)
        this@AlarmCreationFragment.fertilizingInterval    = max(fertilizingInterval, 1)
        this@AlarmCreationFragment.waterSprayingInterval  = max(waterSprayingInterval, 1)

        updateInterval(interval)
        binding.nameEditText.setText(plantName)

        // … остальная логика isEditing / logging …
    }

    private fun pickDate() = DateTimePickerHelper.showDate(
        fragment   = this,
        calendar   = getCurrentCalendar(),
        themeRes   = R.style.CustomDatePickerDialog
    ) { y, m, d ->
        with(viewModel.timeStorage) {
            year = y; month = m; dayOfMonth = d
            binding.dateSelector.text = dateFormated
        }
    }

    private fun pickTime() = DateTimePickerHelper.showTime(
        fragment   = this,
        calendar   = getCurrentCalendar(),
        themeRes   = R.style.CustomTimePickerDialog
    ) { h, m ->
        with(viewModel.timeStorage) {
            hourOfDay = h; minute = m
            binding.timeSelector.text = timeFormated
        }
    }

    private fun updateInterval(raw: Int) = updateInterval(raw.toLong())

    private fun updateInterval(raw: Long) = with(binding) {
        val v = raw.coerceIn(IntervalUtils.MIN.toLong(), IntervalUtils.MAX.toLong())
        viewModel.isUpdating = true
        viewModel.interval = v
        intervalValue.setText(v.toString())
        intervalValue.setSelection(intervalValue.text.length)
        viewModel.isUpdating = false

        when {
            radioWatering.isChecked      -> wateringInterval      = v.toInt()
            radioFertilizing.isChecked   -> fertilizingInterval   = v.toInt()
            radioWaterSpraying.isChecked -> waterSprayingInterval = v.toInt()
        }
    }

    private fun saveAlarmAndExit() {
        // ─── используй старую save-логику, она не изменилась ───
        findNavController().navigate(
            AlarmCreationFragmentDirections.actionAlarmCreationFragmentToAlarmsFragment()
        )
    }

    private fun getCurrentCalendar() = getInstance()
}
