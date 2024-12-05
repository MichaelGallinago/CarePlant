package net.micg.plantcare.domain.useCase

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.alarm.AlarmCreationModel

interface GetAlarmCreationDataUseCase {
    suspend operator fun invoke(fileName: String): HttpResponseState<AlarmCreationModel>
}
