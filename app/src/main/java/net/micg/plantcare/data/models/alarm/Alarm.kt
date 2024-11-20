package net.micg.plantcare.data.models.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: Byte,
    val dateInMillis: Long,
    val intervalInMillis: Long,
    val isEnabled: Boolean
) {
    fun getTypeLabel(): String {
        return when (type) {
            0.toByte() -> "Полив"
            1.toByte() -> "Удобрение"
            else -> "Неизвестно"
        }
    }

    fun getTimeFormatedUntilNextAlarm(): String {
        var currentTime = System.currentTimeMillis()

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
        var days = hours / 24
        minutes %= 60
        hours %= 24

        return buildString {
            if (days > 0) append("$days d ")
            if (hours > 0) append("$hours h ")
            if (minutes > 0  || isEmpty()) append("$minutes min ")
        }.trim()
    }
}
