package net.micg.plantcare.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.util.UUID
import androidx.core.content.edit

object UUIDUtils {
    fun getDeviceUUID(context: Context): String {
        val prefs = context.getSharedPreferences("device_prefs", MODE_PRIVATE)
        var deviceId = prefs.getString("device_id", null)

        if (deviceId != null) return deviceId

        deviceId = UUID.randomUUID().toString()
        prefs.edit { putString("device_id", deviceId) }
        return deviceId
    }
}
