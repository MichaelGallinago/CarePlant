package net.micg.plantcare.presentation.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.micg.plantcare.data.AlarmNotificationService
import net.micg.plantcare.data.AlarmsRepository
import net.micg.plantcare.data.models.alarm.Alarm
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val repository: AlarmsRepository,
    private val alarmNotificationService: AlarmNotificationService
) : ViewModel() {
    private val _allAlarms = MutableLiveData<List<Alarm>>()
    val allAlarms: LiveData<List<Alarm>> get() = _allAlarms

    fun refreshAlarms() = viewModelScope.launch { _allAlarms.value = repository.getAllAlarms() }

    fun insert(alarm: Alarm) = viewModelScope.launch {
        repository.insert(alarm)
        alarmNotificationService.setAlarm(
            alarm.id, alarm.dateInMillis, alarm.intervalInMillis
        )
        refreshAlarms()
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        repository.delete(alarm)
        alarmNotificationService.cancelAlarm(alarm.id)
        refreshAlarms()
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        repository.update(alarm)
        if (alarm.isEnabled) {
            alarmNotificationService.setAlarm(alarm.id, alarm.dateInMillis, alarm.intervalInMillis)
        } else {
            alarmNotificationService.cancelAlarm(alarm.id)
        }
        refreshAlarms()
    }
}
