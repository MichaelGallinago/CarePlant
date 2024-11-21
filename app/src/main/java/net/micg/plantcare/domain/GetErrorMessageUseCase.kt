package net.micg.plantcare.domain

import net.micg.plantcare.service.ErrorMessageService
import javax.inject.Inject

class GetErrorMessageUseCase @Inject constructor(private val service: ErrorMessageService) {
    operator fun invoke(type: ErrorMessageService.Type) = service.getMessage(type)
}