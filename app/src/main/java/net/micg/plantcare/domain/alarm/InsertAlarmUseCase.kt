package net.micg.plantcare.domain.alarm

import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.models.alarm.AlarmEntity
import net.micg.plantcare.service.TypeLabelService

class InsertAlarmUseCase(
    private val repository: AlarmsRepository,
    private val service: TypeLabelService,
) {
    suspend operator fun invoke(
        name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long,
    ) = repository.insert(AlarmEntity(0, name, type, dateInMillis, intervalInMillis, true)).run {
        Alarm(this, name, service.getTypeLabel(type), dateInMillis, intervalInMillis, true)
    }
}
