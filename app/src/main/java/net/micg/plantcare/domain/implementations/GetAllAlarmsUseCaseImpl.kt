package net.micg.plantcare.domain.implementations

import android.content.Context
import net.micg.plantcare.presentation.models.Alarm
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.domain.usecase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.utils.TypeLabelUtils
import javax.inject.Inject

class GetAllAlarmsUseCaseImpl @Inject constructor(
    private val repository: AlarmsRepository,
    private val context: Context,
) : GetAllAlarmsUseCase {
    override suspend operator fun invoke() = repository.getAll().map { entity ->
        entity.run {
            Alarm(
                id,
                name,
                TypeLabelUtils.getTypeLabel(context, type),
                dateInMillis,
                intervalInMillis,
                isEnabled
            )
        }
    }
}
