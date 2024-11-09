package net.micg.plantcare.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val time: String,
    val date: String,
    val isEnabled: Boolean
)
