package net.micg.plantcare.data.alarm.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.micg.plantcare.utils.TypeLabelUtils
import net.micg.plantcare.presentation.models.Alarm

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: Byte,
    val dateInMillis: Long,
    val intervalInMillis: Long,
    val isEnabled: Boolean,
    val isInCalendar: Boolean
) {
    fun toPresentationModel(context: Context): Alarm = Alarm(
        id,
        name,
        TypeLabelUtils.getTypeLabel(context, type),
        dateInMillis,
        intervalInMillis,
        isEnabled,
        isInCalendar
    )
}
