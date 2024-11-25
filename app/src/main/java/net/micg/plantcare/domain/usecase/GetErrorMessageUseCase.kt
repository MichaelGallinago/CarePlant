package net.micg.plantcare.domain.usecase

import net.micg.plantcare.utils.ErrorMessageService

interface GetErrorMessageUseCase {
    operator fun invoke(type: ErrorMessageService.Type): String
}
