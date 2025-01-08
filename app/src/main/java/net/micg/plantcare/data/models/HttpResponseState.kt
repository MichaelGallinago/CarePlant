package net.micg.plantcare.data.models

import net.micg.plantcare.utils.ErrorMessageUtils

sealed class HttpResponseState<out T> {
    data class Success<out T>(val value: T) : HttpResponseState<T>()
    data class Failure(val message: String, val type: ErrorMessageUtils.Type) :
        HttpResponseState<Nothing>()
}
