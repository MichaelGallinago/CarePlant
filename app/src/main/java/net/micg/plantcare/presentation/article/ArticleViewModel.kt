package net.micg.plantcare.presentation.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.alarm.AlarmCreationModel
import net.micg.plantcare.domain.useCase.GetAlarmCreationDataUseCase
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    private val getAlarmCreationDataUseCase: GetAlarmCreationDataUseCase,
) : ViewModel() {
    private val _alarmCreationModule = MutableLiveData<AlarmCreationModel>()
    val alarmCreationModule: LiveData<AlarmCreationModel> get() = _alarmCreationModule

    fun getAlarmCreationData(fileName: String) = viewModelScope.launch(Dispatchers.IO) {
        with(getAlarmCreationDataUseCase(fileName)) {
            when (this) {
                is HttpResponseState.Success -> { _alarmCreationModule.postValue(this.value) }
                is HttpResponseState.Failure -> {}
            }
        }
    }
}
