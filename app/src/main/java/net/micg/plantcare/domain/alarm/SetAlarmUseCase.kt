package net.micg.plantcare.domain.alarm

import net.micg.plantcare.data.alarm.Alarm
import net.micg.plantcare.service.AlarmNotificationService
import javax.inject.Inject

class SetAlarmUseCase @Inject constructor(private val service: AlarmNotificationService) {
    operator fun invoke(alarm: Alarm) = service.setAlarm(alarm)
}
