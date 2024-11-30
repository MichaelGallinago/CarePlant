package net.micg.plantcare.domain.useCase

interface UpdateAlarmUseCase {
    suspend operator fun invoke(isEnabled: Boolean, id: Long)
}
