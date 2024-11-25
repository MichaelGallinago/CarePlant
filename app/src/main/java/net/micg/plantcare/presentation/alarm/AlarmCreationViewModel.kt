package net.micg.plantcare.presentation.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.presentation.models.TimeModel
import net.micg.plantcare.domain.implementations.InsertAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.SetAlarmUseCaseImpl
import javax.inject.Inject

class AlarmCreationViewModel @Inject constructor(
    private val insertAlarmUseCase: InsertAlarmUseCaseImpl,
    private val setAlarmUseCase: SetAlarmUseCaseImpl,
) : ViewModel() {
    val timeStorage: TimeModel = TimeModel(0, 0, 0, 0, 0)

    fun insert(name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            setAlarmUseCase(insertAlarmUseCase(name, type, dateInMillis, intervalInMillis))
        }
}
