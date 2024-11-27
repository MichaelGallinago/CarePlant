package net.micg.plantcare.domain.usecase

import net.micg.plantcare.domain.utils.ErrorMessageUtils

interface GetErrorMessageUseCase {
    operator fun invoke(type: ErrorMessageUtils.Type): String
}
