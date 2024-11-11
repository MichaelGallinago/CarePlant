package net.micg.plantcare.data

import androidx.lifecycle.LiveData
import net.micg.plantcare.data.models.alarm.Alarm
import net.micg.plantcare.data.models.alarm.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override val allAlarms: LiveData<List<Alarm>> = alarmDao.getAllAlarms()

    override suspend fun insert(alarm: Alarm) = alarmDao.insert(alarm)
    override suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)
    override suspend fun update(alarm: Alarm) = alarmDao.update(alarm)
}