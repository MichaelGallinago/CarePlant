package net.micg.plantcare.data

import androidx.lifecycle.LiveData
import net.micg.plantcare.data.models.Alarm

interface AlarmsRepository {
    val allAlarms: LiveData<List<Alarm>>

    suspend fun insert(alarm: Alarm)
    suspend fun delete(alarm: Alarm)
    suspend fun update(alarm: Alarm)
}
