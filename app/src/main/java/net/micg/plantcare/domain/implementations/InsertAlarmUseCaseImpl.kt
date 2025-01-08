package net.micg.plantcare.domain.implementations

import android.content.Context
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.data.alarm.repository.AlarmsRepository
import net.micg.plantcare.data.alarm.models.AlarmEntity
import net.micg.plantcare.domain.useCase.InsertAlarmUseCase
import net.micg.plantcare.utils.TypeLabelUtils
import javax.inject.Inject

class InsertAlarmUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository,
    private val context: Context
) : InsertAlarmUseCase {
    override suspend operator fun invoke(
        name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long,
    ) = repository.insert(
        AlarmEntity(0, name, type, dateInMillis, intervalInMillis, true)
    ).run {
        Alarm(
            this,
            name,
            TypeLabelUtils.getTypeLabel(context, type),
            dateInMillis,
            intervalInMillis,
            true
        )
    }
}
