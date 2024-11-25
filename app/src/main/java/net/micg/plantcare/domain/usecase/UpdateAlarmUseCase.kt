package net.micg.plantcare.domain.usecase

interface UpdateAlarmUseCase {
    suspend operator fun invoke(isEnabled: Boolean, id: Long)
}
