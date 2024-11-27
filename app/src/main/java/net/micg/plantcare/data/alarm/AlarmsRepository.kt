package net.micg.plantcare.data.alarm

import kotlinx.coroutines.flow.Flow
import net.micg.plantcare.data.models.alarm.AlarmEntity

interface AlarmsRepository {
    val allAlarmEntities: Flow<List<AlarmEntity>>

    suspend fun insert(alarm: AlarmEntity): Long
    suspend fun deleteById(id: Long)
    suspend fun update(isEnabled: Boolean, id: Long)
}
