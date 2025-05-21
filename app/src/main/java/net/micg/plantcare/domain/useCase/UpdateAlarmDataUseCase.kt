package net.micg.plantcare.domain.useCase

import net.micg.plantcare.presentation.models.Alarm

interface UpdateAlarmDataUseCase {
    suspend operator fun invoke(
        name: String,
        type: Byte,
        dateInMillis: Long,
        intervalInMillis: Long,
        isInCalendar: Boolean,
        isEnabled: Boolean,
        id: Long
    ): Alarm
}
