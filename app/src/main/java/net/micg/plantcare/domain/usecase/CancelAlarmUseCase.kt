package net.micg.plantcare.domain.usecase

interface CancelAlarmUseCase {
    operator fun invoke(id: Long): Unit?
}
