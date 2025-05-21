package net.micg.plantcare.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.Fragment
import java.util.Calendar

object DateTimePickerHelper {

    fun showDate(
        fragment: Fragment,
        calendar: Calendar,
        themeRes: Int,
        onPicked: (year: Int, month: Int, day: Int) -> Unit
    ) {
        with(calendar) {
            DatePickerDialog(
                fragment.requireContext(),
                themeRes,
                { _, y, m, d -> onPicked(y, m, d) },
                get(Calendar.YEAR),
                get(Calendar.MONTH),
                get(Calendar.DAY_OF_MONTH)
            ).apply { datePicker.minDate = timeInMillis }.show()
        }
    }

    fun showTime(
        fragment: Fragment,
        calendar: Calendar,
        themeRes: Int,
        onPicked: (h: Int, m: Int) -> Unit
    ) {
        with(calendar) {
            TimePickerDialog(
                fragment.requireContext(),
                themeRes,
                { _, h, m -> onPicked(h, m) },
                get(Calendar.HOUR_OF_DAY),
                get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}
