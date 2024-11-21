package net.micg.plantcare.domain.alarm

import net.micg.plantcare.data.alarm.AlarmsRepository

class UpdateAlarmUseCase(private val repository: AlarmsRepository) {
    suspend operator fun invoke(isEnabled: Boolean, id: Long) = repository.update(isEnabled, id)
}
