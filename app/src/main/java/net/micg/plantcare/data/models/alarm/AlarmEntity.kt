package net.micg.plantcare.data.models.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: Byte,
    val dateInMillis: Long,
    val intervalInMillis: Long,
    val isEnabled: Boolean
)
