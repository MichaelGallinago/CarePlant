package net.micg.plantcare.domain.implementations

import net.micg.plantcare.domain.usecase.GetErrorMessageUseCase
import net.micg.plantcare.utils.ErrorMessageService
import javax.inject.Inject

class GetErrorMessageUseCaseImpl @Inject constructor(private val service: ErrorMessageService) :
    GetErrorMessageUseCase {
    override operator fun invoke(type: ErrorMessageService.Type) = service.getMessage(type)
}