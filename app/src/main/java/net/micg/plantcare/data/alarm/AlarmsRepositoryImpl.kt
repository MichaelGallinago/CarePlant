package net.micg.plantcare.data.alarm

import net.micg.plantcare.data.models.alarm.Alarm
import net.micg.plantcare.data.models.alarm.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override suspend fun getAll(): List<Alarm> = alarmDao.getAll()
    override suspend fun update(alarm: Alarm) = alarmDao.update(alarm)
    override suspend fun insert(alarm: Alarm): Long = alarmDao.insert(alarm)
    override suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)
}
