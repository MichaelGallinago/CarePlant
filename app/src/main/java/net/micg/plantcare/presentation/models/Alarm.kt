package net.micg.plantcare.presentation.models

data class Alarm(
    val id: Long,
    val name: String,
    val type: String,
    val dateInMillis: Long,
    val intervalInMillis: Long,
    val isEnabled: Boolean,
    val isInCalendar: Boolean
) {
    fun getFormattedTime(timeConverter: TimeConverter) = timeConverter.run {
        val currentTime = System.currentTimeMillis()

        if (currentTime >= dateInMillis) {
            if (intervalInMillis == 0L) return convertMillisToDateTime(0L)

            val diffMillis = currentTime - dateInMillis
            val nextAlarmTime =
                dateInMillis + ((diffMillis / intervalInMillis) + 1) * intervalInMillis
            return convertMillisToDateTime(nextAlarmTime - currentTime)
        }

        convertMillisToDateTime(dateInMillis - currentTime)
    }
}
