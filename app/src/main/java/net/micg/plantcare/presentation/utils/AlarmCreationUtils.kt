package net.micg.plantcare.presentation.utils

import android.widget.Spinner
import java.util.Calendar
import java.util.Calendar.getInstance

object AlarmCreationUtils {
    @Deprecated("Now interval only in days",
        ReplaceWith("calculateIntervalInMillis(days)")
    )
    fun calculateIntervalInMillis(days: Long, hours: Long, minutes: Long) =
        ((days * 24L + hours) * 60L + minutes) * 60000L

    fun calculateIntervalInMillis(days: Long) =  days * 24L * 60L * 60000L

    fun getCurrentCalendar(): Calendar = getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    fun getSpinnerValue(spinner: Spinner) = spinner.selectedItem.toString().toLong()
}
