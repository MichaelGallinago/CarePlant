package net.micg.plantcare.data.alarm

import net.micg.plantcare.data.models.alarm.Alarm

interface AlarmsRepository {
    suspend fun getAll(): List<Alarm>
    suspend fun insert(alarm: Alarm): Long
    suspend fun delete(alarm: Alarm)
    suspend fun update(alarm: Alarm)
}
