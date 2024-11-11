package net.micg.plantcare.data

import net.micg.plantcare.data.models.alarm.Alarm

interface AlarmsRepository {
    suspend fun getAllAlarms(): List<Alarm>
    suspend fun insert(alarm: Alarm)
    suspend fun delete(alarm: Alarm)
    suspend fun update(alarm: Alarm)
}
