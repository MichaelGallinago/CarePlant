package net.micg.plantcare.domain.alarm

import net.micg.plantcare.data.alarm.Alarm
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.service.TypeLabelService

class GetAllAlarmsUseCase(
    private val repository: AlarmsRepository,
    private val service: TypeLabelService,
) {
    suspend operator fun invoke() = repository.getAll().map { entity ->
        entity.run {
            Alarm(id, name, service.getTypeLabel(type), dateInMillis, intervalInMillis, isEnabled)
        }
    }
}
