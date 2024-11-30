package net.micg.plantcare.domain.implementations

import android.content.Context
import net.micg.plantcare.domain.useCase.SetAlarmUseCase
import net.micg.plantcare.domain.utils.AlarmNotificationUtils
import net.micg.plantcare.presentation.models.Alarm
import javax.inject.Inject

class SetAlarmUseCaseImpl @Inject constructor(private val context: Context) : SetAlarmUseCase {
    override operator fun invoke(alarm: Alarm) = with(alarm) {
        AlarmNotificationUtils.setAlarm(
            context,
            id.toInt(),
            name,
            type,
            AlarmNotificationUtils.getValidDate(dateInMillis, intervalInMillis),
            intervalInMillis
        )
    }
}
