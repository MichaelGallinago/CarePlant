package net.micg.plantcare.utils

import android.content.Context
import android.os.Bundle
import net.micg.plantcare.presentation.alarmCreation.AlarmCreationViewModel

class AlarmHelper(
    private val context: Context,
    private val viewModel: AlarmCreationViewModel
) {
    var isEditing = false
    var editingId = 0L
    var editingIsEnabled = true
    var wasAlarmSaved = false

    fun setEditing(id: Long, isEnabled: Boolean) {
        isEditing = true
        editingId = id
        editingIsEnabled = isEnabled
    }

    fun save(
        name: String, type: Byte, dateInMillis: Long, intervalDays: Int, isInCalendar: Boolean
    ) {
        val intervalMillis = calculateIntervalInMillis(intervalDays)

        if (isEditing) {
            viewModel.updateData(
                editingId, name, type, dateInMillis, intervalMillis, isInCalendar, editingIsEnabled
            )
            logAlarmEvent(
                FirebaseUtils.EDITED_NOTIFICATIONS, name, type, dateInMillis, intervalDays
            )
        } else {
            viewModel.insert(name, type, dateInMillis, intervalMillis, isInCalendar)
            logAlarmEvent(
                FirebaseUtils.CREATED_NOTIFICATIONS, name, type, dateInMillis, intervalDays
            )
        }

        wasAlarmSaved = true
    }

    fun getTypeName(type: Byte): String = when (type.toInt()) {
        0 -> "watering"
        1 -> "fertilizing"
        2 -> "transplanting"
        3 -> "water_spraying"
        else -> "unknown"
    }

    private fun calculateIntervalInMillis(days: Int): Long = days * 24L * 60L * 60L * 1000L

    private fun logAlarmEvent(
        eventName: String, name: String, type: Byte, dateInMillis: Long, intervalDays: Int
    ) = FirebaseUtils.logEvent(context, eventName, Bundle().apply {
        putString("type", getTypeName(type))
        putString("name", name)
        putString("date", AlarmCreationUtils.convertTimeToString(dateInMillis))
        putLong("interval", intervalDays.toLong())
    })
}
