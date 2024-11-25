package net.micg.plantcare.domain.implementations

import net.micg.plantcare.domain.usecase.SetAlarmUseCase
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.utils.AlarmNotificationService
import javax.inject.Inject

class SetAlarmUseCaseImpl @Inject constructor(private val service: AlarmNotificationService) :
    SetAlarmUseCase {
    override operator fun invoke(alarm: Alarm) = service.setAlarm(alarm)
}
