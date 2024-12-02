package net.micg.plantcare.presentation.models

data class Alarm(
    val id: Long,
    val name: String,
    val type: String,
    val dateInMillis: Long,
    val intervalInMillis: Long,
    val isEnabled: Boolean,
) {
    val time: String
        get() {
            val currentTime = System.currentTimeMillis()

            if (currentTime >= dateInMillis) {
                if (intervalInMillis == 0L) return convertMillisToDateTime(0L)

                val diffMillis = currentTime - dateInMillis
                val nextAlarmTime =
                    dateInMillis + ((diffMillis / intervalInMillis) + 1) * intervalInMillis
                return convertMillisToDateTime(nextAlarmTime - currentTime)
            }

            return convertMillisToDateTime(dateInMillis - currentTime)
        }

    private fun convertMillisToDateTime(millis: Long): String {
        var minutes = millis / 1000 / 60
        var hours = minutes / 60
        val days = hours / 24
        minutes %= 60
        hours %= 24

        return buildString { //TODO: change to Russian somehow
            if (days > 0) append("$days d ")
            if (hours > 0) append("$hours h ")
            if (minutes > 0 || isEmpty()) append("$minutes min ")
        }.trim()
    }
}
