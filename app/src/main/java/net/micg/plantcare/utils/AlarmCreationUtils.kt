package net.micg.plantcare.utils

import android.widget.Spinner
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.getInstance
import java.util.Date
import java.util.Locale

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

    fun convertTimeToString(timeInMillis: Long): String = 
        SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date(timeInMillis))

    fun getTypeName(type: Int): String = when(type) {
        0 -> "Watering"
        1 -> "Fertilizing"
        2 -> "Transplanting"
        3 -> "WaterSpreading"
        else -> ""
    }
}
