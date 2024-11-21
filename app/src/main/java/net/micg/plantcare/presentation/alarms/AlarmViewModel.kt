package net.micg.plantcare.presentation.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.micg.plantcare.di.AlarmNotificationModule
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.models.alarm.Alarm
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val repository: AlarmsRepository,
    private val alarmNotificationModule: AlarmNotificationModule
) : ViewModel() {
    private val _allAlarms = MutableLiveData<List<Alarm>>()
    val allAlarms: LiveData<List<Alarm>> get() = _allAlarms

    fun refreshAlarms() = viewModelScope.launch { _allAlarms.value = repository.getAll() }

    fun insert(alarm: Alarm) = runBlocking {
        setAlarm(repository.insert(alarm), alarm)
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
            setAlarm(alarm.id, alarm)
        } else {
            alarmNotificationModule.cancelAlarm(alarm.id)
        }
        refreshAlarms()
    }

    private fun setAlarm(id: Long, alarm: Alarm) = with(alarm) {
        alarmNotificationModule.setAlarm(id, name, getTypeLabel(), dateInMillis, intervalInMillis)
    }
}
