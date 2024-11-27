package net.micg.plantcare.presentation.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.presentation.models.TimeModel
import net.micg.plantcare.domain.usecase.InsertAlarmUseCase
import net.micg.plantcare.domain.usecase.SetAlarmUseCase
import javax.inject.Inject

class AlarmCreationViewModel @Inject constructor(
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
) : ViewModel() {
    val timeStorage: TimeModel = TimeModel(0, 0, 0, 0, 0)
    var interval: Long = 0L

    private val _isCreationFinished = MutableLiveData<Boolean>()
    val isCreationFinished: LiveData<Boolean> get() = _isCreationFinished
    var isCreationStarted: Boolean = false

    fun insert(name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            setAlarmUseCase(insertAlarmUseCase(name, type, dateInMillis, intervalInMillis))
            _isCreationFinished.postValue(true)
        }
}
