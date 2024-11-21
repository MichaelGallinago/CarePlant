package net.micg.plantcare.presentation.utils

import android.widget.Spinner
import java.util.Calendar.getInstance

object AlarmCreationUtils {
    fun calculateIntervalInMillis(days: Long, hours: Long, minutes: Long) =
        ((days * 24L + hours) * 60L + minutes) * 60000L

    fun getCurrentCalendar() = getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    fun getSpinnerValue(spinner: Spinner) = spinner.selectedItem.toString().toLong()
}
