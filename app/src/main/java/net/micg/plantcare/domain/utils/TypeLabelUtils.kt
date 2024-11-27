package net.micg.plantcare.domain.utils

import android.content.Context
import net.micg.plantcare.R

object TypeLabelUtils {
    fun getTypeLabel(context: Context, type: Byte) = context.getString(
        when (type) {
            0.toByte() -> R.string.type_watering
            1.toByte() -> R.string.type_fertilizing
            else -> R.string.type_unknown
        }
    )
}
