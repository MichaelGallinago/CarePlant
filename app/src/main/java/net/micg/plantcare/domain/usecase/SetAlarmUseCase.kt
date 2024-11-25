package net.micg.plantcare.domain.usecase

import net.micg.plantcare.presentation.models.Alarm

interface SetAlarmUseCase {
    operator fun invoke(alarm: Alarm)
}
