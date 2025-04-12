package net.micg.plantcare.domain.implementations

import android.content.Context
import net.micg.plantcare.data.alarm.models.AlarmEntity
import net.micg.plantcare.data.alarm.repository.AlarmsRepository
import net.micg.plantcare.domain.useCase.UpdateAlarmDataUseCase
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.utils.TypeLabelUtils
import javax.inject.Inject

class UpdateAlarmDataUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository,
    private val context: Context
) : UpdateAlarmDataUseCase {
    override suspend operator fun invoke(
        name: String,
        type: Byte,
        dateInMillis: Long,
        intervalInMillis: Long,
        isEnabled: Boolean,
        id: Long,
    ) = repository.updateData(
        AlarmEntity(id, name, type, dateInMillis, intervalInMillis, isEnabled)
    ).run {
        Alarm(
            id,
            name,
            TypeLabelUtils.getTypeLabel(context, type),
            dateInMillis,
            intervalInMillis,
            isEnabled
        )
    }
}
