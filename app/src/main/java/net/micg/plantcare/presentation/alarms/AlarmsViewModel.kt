package net.micg.plantcare.presentation.alarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.domain.useCase.CancelAlarmUseCase
import net.micg.plantcare.domain.useCase.DeleteAlarmByIdUseCase
import net.micg.plantcare.domain.useCase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.useCase.SetAlarmUseCase
import net.micg.plantcare.domain.useCase.UpdateAlarmUseCase
import net.micg.plantcare.presentation.models.Alarm
import javax.inject.Inject

class AlarmsViewModel @Inject constructor(
    private val deleteAlarmUseCase: DeleteAlarmByIdUseCase,
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
    private val cancelAlarmUseCase: CancelAlarmUseCase,
) : ViewModel() {
    val allAlarms: LiveData<List<Alarm>> get() = getAllAlarmsUseCase()

    fun delete(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        deleteAlarmUseCase(alarm.id)
        cancelAlarmUseCase(alarm.id)
    }

    fun update(isEnabled: Boolean, alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        updateAlarmUseCase(isEnabled, alarm.id)

        if (isEnabled) {
            setAlarmUseCase(alarm)
        } else {
            cancelAlarmUseCase(alarm.id)
        }
    }
}
