package net.micg.plantcare.presentation.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import net.micg.plantcare.data.AlarmNotificationModule
import net.micg.plantcare.data.AlarmsRepository
import net.micg.plantcare.data.models.alarm.Alarm
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val repository: AlarmsRepository,
    private val alarmNotificationModule: AlarmNotificationModule
) : ViewModel() {
    private val _allAlarms = MutableLiveData<List<Alarm>>()
    val allAlarms: LiveData<List<Alarm>> get() = _allAlarms

    fun refreshAlarms() = runBlocking { _allAlarms.value = repository.getAllAlarms() }

    fun insert(alarm: Alarm) = runBlocking {
        repository.insert(alarm)
        setAlarm(alarm)
        refreshAlarms()
    }

    fun delete(alarm: Alarm) = runBlocking {
        repository.delete(alarm)
        alarmNotificationModule.cancelAlarm(alarm.id)
        refreshAlarms()
    }

    fun update(alarm: Alarm) = runBlocking {
        repository.update(alarm)
        if (alarm.isEnabled) {
            setAlarm(alarm)
        } else {
            alarmNotificationModule.cancelAlarm(alarm.id)
        }
        refreshAlarms()
    }

    private fun setAlarm(alarm: Alarm) = with(alarm) {
        alarmNotificationModule.setAlarm(id, name, getTypeLabel(), dateInMillis, intervalInMillis)
    }
}
