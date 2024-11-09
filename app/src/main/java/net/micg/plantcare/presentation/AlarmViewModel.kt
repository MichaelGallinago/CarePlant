package net.micg.plantcare.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.micg.plantcare.data.AlarmRepository
import net.micg.plantcare.data.models.Alarm
import javax.inject.Inject

class AlarmViewModel @Inject constructor(private val repository: AlarmRepository) : ViewModel() {
    val allAlarms: LiveData<List<Alarm>> = repository.allAlarms

    fun insert(alarm: Alarm) = viewModelScope.launch { repository.insert(alarm) }
    fun delete(alarm: Alarm) = viewModelScope.launch { repository.delete(alarm) }
    fun update(alarm: Alarm) = viewModelScope.launch { repository.update(alarm) }
}