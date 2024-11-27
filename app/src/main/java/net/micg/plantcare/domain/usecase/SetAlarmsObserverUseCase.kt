package net.micg.plantcare.domain.usecase

import androidx.lifecycle.LiveData
import net.micg.plantcare.presentation.models.Alarm

interface SetAlarmsObserverUseCase {
    operator fun invoke(): LiveData<List<Alarm>>
}
