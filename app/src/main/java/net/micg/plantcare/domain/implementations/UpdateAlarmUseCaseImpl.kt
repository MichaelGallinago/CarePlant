package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.alarm.repository.AlarmsRepository
import net.micg.plantcare.domain.useCase.UpdateAlarmUseCase
import javax.inject.Inject

class UpdateAlarmUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository
) : UpdateAlarmUseCase {
    override suspend operator fun invoke(isEnabled: Boolean, id: Long) =
        repository.update(isEnabled, id)
}
