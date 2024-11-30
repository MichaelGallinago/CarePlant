package net.micg.plantcare.domain.useCase

interface CancelAlarmUseCase {
    operator fun invoke(id: Long): Unit?
}
