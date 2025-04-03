package net.micg.plantcare.domain.useCase

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.alarm.models.AlarmCreationModel

interface GetAlarmCreationDataUseCase {
    suspend operator fun invoke(
        locale: String, fileName: String,
    ): HttpResponseState<AlarmCreationModel>
}
