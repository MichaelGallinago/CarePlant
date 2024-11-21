package net.micg.plantcare.domain.alarm

import net.micg.plantcare.data.alarm.AlarmsRepository

class DeleteAlarmByIdUseCase(private val repository: AlarmsRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteById(id)
}
