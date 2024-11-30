package net.micg.plantcare.data.alarm

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.data.db.alarm.AlarmDao
import javax.inject.Inject

class AlarmsRepositoryImpl @Inject constructor(private val alarmDao: AlarmDao) : AlarmsRepository {
    override val allAlarmEntities: Flow<List<AlarmEntity>> = alarmDao.getAll()

    @WorkerThread
    override suspend fun update(isEnabled: Boolean, id: Long) = alarmDao.update(isEnabled, id)

    @WorkerThread
    override suspend fun insert(alarm: AlarmEntity) = alarmDao.insert(alarm)

    @WorkerThread
    override suspend fun deleteById(id: Long) = alarmDao.deleteById(id)
}
