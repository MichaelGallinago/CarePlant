package net.micg.plantcare.data.alarm

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.data.models.alarm.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override val allAlarmEntities: Flow<List<AlarmEntity>> = alarmDao.getAll()

    @WorkerThread
    override suspend fun update(isEnabled: Boolean, id: Long) {
        alarmDao.update(isEnabled, id)
    }

    @WorkerThread
    override suspend fun insert(alarm: AlarmEntity): Long {
        val id = alarmDao.insert(alarm)
        Log.d("alarm_debug", "Inserted $id")
        return id
    }

    @WorkerThread
    override suspend fun deleteById(id: Long) {
        alarmDao.deleteById(id)
    }
}
