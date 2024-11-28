package net.micg.plantcare.presentation.utils

import android.util.Log
import android.widget.Spinner
import net.micg.plantcare.presentation.models.Alarm
import java.util.Calendar
import java.util.Calendar.getInstance

object AlarmCreationUtils {
    @Deprecated("Now interval only in days",
        ReplaceWith("calculateIntervalInMillis(days)")
    )
    fun calculateIntervalInMillis(days: Long, hours: Long, minutes: Long) =
        ((days * 24L + hours) * 60L + minutes) * 60000L

    fun calculateIntervalInMillis(days: Long) = days * 24L * 60L * 60 * 1000L

    fun getCurrentCalendar(): Calendar = getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    @Deprecated("There are no spinners now")
    fun getSpinnerValue(spinner: Spinner) = spinner.selectedItem.toString().toLong()

    fun logAlarm(alarm: Alarm) =
        Log.d("alarm_debug", "${alarm.id}: ${alarm.name} |${alarm.isEnabled}")
}
