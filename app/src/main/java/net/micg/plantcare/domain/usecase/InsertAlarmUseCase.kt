package net.micg.plantcare.domain.usecase

import net.micg.plantcare.presentation.models.Alarm

interface InsertAlarmUseCase {
    suspend operator fun invoke(
        name: String, type: Byte, dateInMillis: Long, intervalInMillis: Long,
    ): Alarm
}
