package net.micg.plantcare.domain.utils

import android.content.Context
import net.micg.plantcare.R

object ErrorMessageUtils {
    enum class Type { LoadingError, ExtendedLoadingError }

    fun getMessage(context: Context, type: Type) = when (type) {
        Type.LoadingError -> context.getString(R.string.error_loading)
        Type.ExtendedLoadingError -> context.getString(R.string.error_loading_extended)
        else -> ""
    }
}
