package net.micg.plantcare.service

import android.content.Context
import net.micg.plantcare.R

class ErrorMessageService(private val context: Context) {
    enum class Type { LoadingError, ExtendedLoadingError }

    fun getMessage(type: Type) = when (type) {
        Type.LoadingError -> context.getString(R.string.error_loading)
        Type.ExtendedLoadingError -> context.getString(R.string.error_loading_extended)
        else -> ""
    }
}
