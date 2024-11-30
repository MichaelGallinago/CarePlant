package net.micg.plantcare.domain.useCase

import net.micg.plantcare.presentation.models.Alarm

interface SetAlarmUseCase {
    operator fun invoke(alarm: Alarm): Unit?
}
