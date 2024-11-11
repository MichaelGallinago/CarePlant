package net.micg.plantcare.data.models.alarm

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Locale

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: Byte,
    val timeInMinutes: Int,
    val daysOfWeekFlags: Int,
    val isEnabled: Boolean
) {
    fun getTypeLabel(): String {
        return when (type) {
            0.toByte() -> "Полив"
            1.toByte() -> "Удобрение"
            else -> "Неизвестно"
        }
    }

    fun getTimeFormatted(): String {
        val hours = timeInMinutes / 60
        val minutes = timeInMinutes % 60
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
    }

    fun getDaysOfWeekLabel(): String {
        return days
            .filterIndexed { index, _ -> (daysOfWeekFlags shr index) and 1 == 1 }
            .joinToString(", ")
    }

    companion object {
        private val days = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    }
}

class Converters {
    @TypeConverter
    fun fromDaysOfWeekFlags(flags: Int): List<WeekDay> {
        return WeekDay.entries.filter { (flags and it.flag) != 0 }
    }

    @TypeConverter
    fun toDaysOfWeekFlags(days: List<WeekDay>): Int {
        return days.fold(0) { acc, day -> acc or day.flag }
    }
}
