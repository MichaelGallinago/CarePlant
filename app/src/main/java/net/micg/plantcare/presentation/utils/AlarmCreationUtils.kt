package net.micg.plantcare.presentation.utils

import android.widget.Spinner
import java.util.Calendar.getInstance

object AlarmCreationUtils {
    fun calculateIntervalInMillis(days: Long, hours: Long, minutes: Long) =
        ((days * 24L + hours) * 60L + minutes) * 60000L

    fun getCurrentCalendar() = getInstance().apply {
        timeInMillis = System.currentTimeMillis()
    }

    fun getDateFormated(year: Int, month: Int, day: Int) =
        "%02d/%02d/%04d".format(day, month + 1, year)

    fun getTimeFormated(hour: Int, minute: Int) =
        "%02d:%02d".format(hour, minute)

    fun getSpinnerValue(spinner: Spinner) =
        spinner.selectedItem.toString().toLong()
}
