package net.micg.plantcare.domain.implementations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.domain.usecase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.utils.TypeLabelUtils
import javax.inject.Inject
import kotlin.collections.map

class GetAllAlarmsUseCaseImpl @Inject constructor(
    repository: AlarmsRepository,
    private val context: Context,
) : GetAllAlarmsUseCase {
    private val _allAlarms = repository.allAlarmEntities.map { entities ->
        entities.map { it.toPresentationModel() }
    }
    override val allAlarms: LiveData<List<Alarm>> get() = _allAlarms

    private fun AlarmEntity.toPresentationModel(): Alarm = Alarm(
        id,
        name,
        TypeLabelUtils.getTypeLabel(context, type),
        dateInMillis,
        intervalInMillis,
        isEnabled
    )
}
