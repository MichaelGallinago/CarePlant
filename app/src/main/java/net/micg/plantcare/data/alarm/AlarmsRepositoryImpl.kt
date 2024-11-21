package net.micg.plantcare.data.alarm

import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.data.models.alarm.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override suspend fun getAll(): List<AlarmEntity> = alarmDao.getAll()
    override suspend fun update(isEnabled: Boolean, id: Long) = alarmDao.update(isEnabled, id)
    override suspend fun insert(alarm: AlarmEntity): Long = alarmDao.insert(alarm)
    override suspend fun deleteById(id: Long) = alarmDao.deleteById(id)
}
