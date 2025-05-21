package net.micg.plantcare.domain.useCase

import net.micg.plantcare.presentation.models.Alarm

interface InsertAlarmUseCase {
    suspend operator fun invoke(
        name: String,
        type: Byte,
        dateInMillis: Long,
        intervalInMillis: Long,
        isEnabled: Boolean,
        isInCalendar: Boolean
    ): Alarm
}
