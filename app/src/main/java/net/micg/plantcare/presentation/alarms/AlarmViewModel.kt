package net.micg.plantcare.presentation.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.micg.plantcare.data.AlarmsRepository
import net.micg.plantcare.data.models.alarm.Alarm
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val repository: AlarmsRepository
) : ViewModel() {
    private val _allAlarms = MutableLiveData<List<Alarm>>()
    val allAlarms: LiveData<List<Alarm>> get() = _allAlarms

    fun updateAlarms() = viewModelScope.launch { _allAlarms.value = repository.getAllAlarms() }

    fun insert(alarm: Alarm) = viewModelScope.launch {
        repository.insert(alarm)
        updateAlarms()
    }

    fun delete(alarm: Alarm) = viewModelScope.launch {
        repository.delete(alarm)
        updateAlarms()
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        repository.update(alarm)
        updateAlarms()
    }
}
