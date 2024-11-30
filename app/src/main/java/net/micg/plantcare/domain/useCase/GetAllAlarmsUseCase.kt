package net.micg.plantcare.domain.useCase

import kotlinx.coroutines.flow.Flow
import net.micg.plantcare.presentation.models.Alarm

interface GetAllAlarmsUseCase {
    operator fun invoke(): Flow<List<Alarm>>
}
