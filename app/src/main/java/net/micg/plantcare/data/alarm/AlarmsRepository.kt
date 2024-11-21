package net.micg.plantcare.data.alarm

import net.micg.plantcare.data.models.alarm.AlarmEntity

interface AlarmsRepository {
    suspend fun getAll(): List<AlarmEntity>
    suspend fun insert(alarm: AlarmEntity): Long
    suspend fun deleteById(id: Long)
    suspend fun update(isEnabled: Boolean, id: Long)
}
