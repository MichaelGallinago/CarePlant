package net.micg.plantcare.data

import androidx.lifecycle.LiveData
import net.micg.plantcare.data.models.Alarm
import net.micg.plantcare.data.models.AlarmDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {
    val allAlarms: LiveData<List<Alarm>> = alarmDao.getAllAlarms()

    suspend fun insert(alarm: Alarm) = alarmDao.insert(alarm)
    suspend fun delete(alarm: Alarm) = alarmDao.delete(alarm)
    suspend fun update(alarm: Alarm) = alarmDao.update(alarm)
}