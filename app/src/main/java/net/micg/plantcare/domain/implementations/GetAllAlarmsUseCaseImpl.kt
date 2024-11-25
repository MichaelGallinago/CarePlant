package net.micg.plantcare.domain.implementations

import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.domain.usecase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.utils.TypeLabelUtils

class GetAllAlarmsUseCaseImpl(
    private val repository: AlarmsRepository,
    private val service: TypeLabelUtils,
) : GetAllAlarmsUseCase {
    override suspend operator fun invoke() = repository.getAll().map { entity ->
        entity.run {
            Alarm(id, name, service.getTypeLabel(type), dateInMillis, intervalInMillis, isEnabled)
        }
    }
}
