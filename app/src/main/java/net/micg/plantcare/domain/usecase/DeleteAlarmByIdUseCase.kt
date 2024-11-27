package net.micg.plantcare.domain.usecase

interface DeleteAlarmByIdUseCase {
    suspend operator fun invoke(id: Long)
}
