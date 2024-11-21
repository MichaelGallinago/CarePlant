package net.micg.plantcare.domain.alarm

import net.micg.plantcare.service.AlarmNotificationService
import javax.inject.Inject

class CancelAlarmUseCase @Inject constructor(private val service: AlarmNotificationService) {
    operator fun invoke(id: Long) = service.cancelAlarm(id)
}
