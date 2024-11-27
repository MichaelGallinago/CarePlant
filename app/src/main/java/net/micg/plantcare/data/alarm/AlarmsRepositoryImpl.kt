package net.micg.plantcare.data.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.data.models.alarm.AlarmDao
import net.micg.plantcare.presentation.models.Alarm
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    private val _allAlarmEntities = MutableLiveData<List<AlarmEntity>>()
    override val allAlarmEntities: LiveData<List<AlarmEntity>> get() = _allAlarmEntities

    init { runBlocking { updateAll() } } // TODO: are you serious?

    override suspend fun update(isEnabled: Boolean, id: Long) {
        alarmDao.update(isEnabled, id)
        updateAll()
    }

    override suspend fun insert(alarm: AlarmEntity): Long {
        val id = alarmDao.insert(alarm)
        updateAll()
        return id
    }

    override suspend fun deleteById(id: Long) {
        alarmDao.deleteById(id)
        updateAll()
    }

    private suspend fun updateAll() = _allAlarmEntities.postValue(alarmDao.getAll())
}
