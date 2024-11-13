package net.micg.plantcare.data.models.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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

    fun getIntervalFormatted() = convertMillisToDateTime(intervalInMillis)
    fun getDateFormatted() = convertMillisToDateTime(dateInMillis)

    private fun convertMillisToDateTime(millis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val date = Date(millis)
        return dateFormat.format(date)
    }
}
