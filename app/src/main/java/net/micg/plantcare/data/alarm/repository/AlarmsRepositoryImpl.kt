package net.micg.plantcare.data.alarm.repository

import kotlinx.coroutines.flow.Flow
import net.micg.plantcare.data.alarm.models.AlarmEntity
import net.micg.plantcare.data.alarm.db.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override val allAlarmEntities: Flow<List<AlarmEntity>> = alarmDao.getAll()

    override suspend fun update(isEnabled: Boolean, id: Long) = alarmDao.update(isEnabled, id)
    override suspend fun insert(alarm: AlarmEntity) = alarmDao.insert(alarm)
    override suspend fun deleteById(id: Long) = alarmDao.deleteById(id)
}
