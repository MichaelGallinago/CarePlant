package net.micg.plantcare.domain.implementations

import android.content.Context
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.domain.usecase.SetAlarmsObserverUseCase
import net.micg.plantcare.domain.utils.TypeLabelUtils
import net.micg.plantcare.presentation.models.Alarm
import javax.inject.Inject
import kotlin.collections.map

class SetAlarmsObserverUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository,
    private val context: Context,
) : SetAlarmsObserverUseCase {
    override operator fun invoke() = repository.allAlarmEntities.map {
        it.map { it.toPresentationModel() }
    }.asLiveData()

    private fun AlarmEntity.toPresentationModel(): Alarm = Alarm(
        id,
        name,
        TypeLabelUtils.getTypeLabel(context, type),
        dateInMillis,
        intervalInMillis,
        isEnabled
    )
}
