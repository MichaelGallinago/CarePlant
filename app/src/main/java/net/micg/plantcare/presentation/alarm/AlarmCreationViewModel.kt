package net.micg.plantcare.presentation.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.presentation.models.TimeModel
import net.micg.plantcare.domain.usecase.InsertAlarmUseCase
import net.micg.plantcare.domain.usecase.SetAlarmUseCase
import net.micg.plantcare.presentation.utils.AlarmCreationUtils
import javax.inject.Inject

class AlarmCreationViewModel @Inject constructor(
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
) : ViewModel() {
    val timeStorage: TimeModel = TimeModel(0, 0, 0, 0, 0)
    var interval: Long = 0L

    fun insert(name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            var alarm = insertAlarmUseCase(name, type, dateInMillis, intervalInMillis)
            setAlarmUseCase(alarm)
            AlarmCreationUtils.logAlarm(alarm) //TODO: remove
        }
}
