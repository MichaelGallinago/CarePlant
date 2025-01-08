package net.micg.plantcare.domain.implementations

import android.content.Context
import net.micg.plantcare.domain.useCase.GetErrorMessageUseCase
import net.micg.plantcare.utils.ErrorMessageUtils
import javax.inject.Inject

class GetErrorMessageUseCaseImpl @Inject constructor(
    private val context: Context
) : GetErrorMessageUseCase {
    override operator fun invoke(type: ErrorMessageUtils.Type) =
        ErrorMessageUtils.getMessage(context, type)
}
