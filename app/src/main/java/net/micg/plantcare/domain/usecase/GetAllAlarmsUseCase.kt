package net.micg.plantcare.domain.usecase

import net.micg.plantcare.presentation.models.Alarm

interface GetAllAlarmsUseCase {
    suspend operator fun invoke(): List<Alarm>
}
