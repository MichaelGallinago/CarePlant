package net.micg.plantcare.domain.alarm

import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.models.alarm.AlarmEntity

class DeleteAlarmByIdUseCase(private val repository: AlarmsRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteById(id)
}
