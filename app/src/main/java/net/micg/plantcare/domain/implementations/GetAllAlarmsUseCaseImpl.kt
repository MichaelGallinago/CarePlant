package net.micg.plantcare.domain.implementations

import android.content.Context
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import net.micg.plantcare.data.alarm.repository.AlarmsRepository
import net.micg.plantcare.domain.useCase.GetAllAlarmsUseCase
import javax.inject.Inject
import kotlin.collections.map

class GetAllAlarmsUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository,
    private val context: Context,
) : GetAllAlarmsUseCase {
    override operator fun invoke() = repository.allAlarmEntities.map { alarmEntities ->
        alarmEntities.map { it.toPresentationModel(context) }
    }.asLiveData()
}
