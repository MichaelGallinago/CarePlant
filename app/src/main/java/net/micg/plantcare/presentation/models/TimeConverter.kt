package net.micg.plantcare.presentation.models

import javax.inject.Inject

class TimeConverter @Inject constructor(private val timeLocalization: TimeLocalization) {
    fun convertMillisToDateTime(millis: Long): String {
        var minutes = millis / 1000 / 60
        var hours = minutes / 60
        val days = hours / 24
        minutes %= 60
        hours %= 24

        return timeLocalization.formatTime(days, hours, minutes)
    }
}