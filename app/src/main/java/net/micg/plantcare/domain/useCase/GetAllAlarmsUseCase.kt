package net.micg.plantcare.domain.useCase

import androidx.lifecycle.LiveData
import net.micg.plantcare.presentation.models.Alarm

interface GetAllAlarmsUseCase {
    operator fun invoke(): LiveData<List<Alarm>>
}
