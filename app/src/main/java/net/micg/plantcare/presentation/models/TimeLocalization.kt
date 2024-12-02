package net.micg.plantcare.presentation.models

import android.content.Context
import javax.inject.Inject
import net.micg.plantcare.R

class TimeLocalization @Inject constructor(private val context: Context) {
    fun formatTime(days: Long, hours: Long, minutes: Long) = context.run {
        buildString {
            if (days > 0) append(getString(R.string.time_days, days)).append(" ")
            if (hours > 0) append(getString(R.string.time_hours, hours)).append(" ")
            if (minutes > 0 || isEmpty()) append(getString(R.string.time_minutes, minutes))
        }.trim()
    }
}
