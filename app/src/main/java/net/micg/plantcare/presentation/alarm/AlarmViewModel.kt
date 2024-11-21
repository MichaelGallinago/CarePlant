package net.micg.plantcare.presentation.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.micg.plantcare.data.alarm.Alarm
import net.micg.plantcare.data.alarm.TimeStorage
import net.micg.plantcare.domain.alarm.CancelAlarmUseCase
import net.micg.plantcare.domain.alarm.DeleteAlarmByIdUseCase
import net.micg.plantcare.domain.alarm.GetAllAlarmsUseCase
import net.micg.plantcare.domain.alarm.InsertAlarmUseCase
import net.micg.plantcare.domain.alarm.SetAlarmUseCase
import net.micg.plantcare.domain.alarm.UpdateAlarmUseCase
import javax.inject.Inject

class AlarmViewModel @Inject constructor(
    private val deleteAlarmUseCase: DeleteAlarmByIdUseCase,
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
) : ViewModel() {
    private val _allAlarms = MutableLiveData<List<Alarm>>()
    val allAlarms: LiveData<List<Alarm>> get() = _allAlarms
    val timeStorage: TimeStorage = TimeStorage(0, 0, 0, 0, 0)

    fun refreshAlarms() = viewModelScope.launch { _allAlarms.value = getAllAlarmsUseCase() }

    fun insert(name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long) = runBlocking {
        setAlarm(insertAlarmUseCase(name, type, dateInMillis, intervalInMillis))
        refreshAlarms()
    }

    fun delete(alarm: Alarm) = runBlocking {
        deleteAlarmUseCase(alarm.id)
        cancelAlarmUseCase(alarm.id)
        refreshAlarms()
    }

    fun update(isEnabled: Boolean, alarm: Alarm) = runBlocking {
        updateAlarmUseCase(isEnabled, alarm.id)
        if (isEnabled) {
            setAlarm(alarm)
        } else {
            cancelAlarmUseCase(alarm.id)
        }
        refreshAlarms()
    }

    private fun setAlarm(alarm: Alarm) = setAlarmUseCase(alarm)
}
