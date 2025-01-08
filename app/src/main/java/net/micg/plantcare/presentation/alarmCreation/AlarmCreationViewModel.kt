package net.micg.plantcare.presentation.alarmCreation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.presentation.models.TimeModel
import net.micg.plantcare.domain.useCase.InsertAlarmUseCase
import net.micg.plantcare.domain.useCase.SetAlarmUseCase
import javax.inject.Inject

class AlarmCreationViewModel @Inject constructor(
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
) : ViewModel() {
    val timeStorage = TimeModel(0, 0, 0, 0, 0)
    var interval = 0L
    var isUpdating = false

    fun insert(
        name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long,
    ) = CoroutineScope(Dispatchers.IO).launch {
        setAlarmUseCase(insertAlarmUseCase(name, type, dateInMillis, intervalInMillis))
    }
}
