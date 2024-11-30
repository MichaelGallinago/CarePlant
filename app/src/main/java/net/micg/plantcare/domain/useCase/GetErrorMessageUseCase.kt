package net.micg.plantcare.domain.useCase

import net.micg.plantcare.domain.utils.ErrorMessageUtils

interface GetErrorMessageUseCase {
    operator fun invoke(type: ErrorMessageUtils.Type): String
}
