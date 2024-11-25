package net.micg.plantcare.domain.implementations

import net.micg.plantcare.domain.usecase.CancelAlarmUseCase
import net.micg.plantcare.utils.AlarmNotificationService
import javax.inject.Inject

class CancelAlarmUseCaseImpl @Inject constructor(private val service: AlarmNotificationService) :
    CancelAlarmUseCase {
    override operator fun invoke(id: Long) = service.cancelAlarm(id)
}
