package net.micg.plantcare.domain.useCase

interface DeleteAlarmByIdUseCase {
    suspend operator fun invoke(id: Long)
}
