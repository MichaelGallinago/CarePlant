package net.micg.plantcare.service

import android.content.Context
import net.micg.plantcare.R

class TypeLabelService(private val context: Context) {
    fun getTypeLabel(type: Byte) = context.getString(
        when (type) {
            0.toByte() -> R.string.type_watering
            1.toByte() -> R.string.type_fertilizing
            else -> R.string.type_unknown
        }
    )
}
