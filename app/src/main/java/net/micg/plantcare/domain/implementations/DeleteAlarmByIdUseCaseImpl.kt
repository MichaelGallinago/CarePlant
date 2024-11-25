package net.micg.plantcare.domain.implementations

import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.domain.usecase.DeleteAlarmByIdUseCase
import javax.inject.Inject

class DeleteAlarmByIdUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository
) : DeleteAlarmByIdUseCase {
    override suspend operator fun invoke(id: Long) = repository.deleteById(id)
}
