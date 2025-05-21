package net.micg.plantcare.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

object CalendarSharedPrefs {
    fun isSwitchEnabled(context: Context) = context
        .getSharedPreferences("alarm_prefs", MODE_PRIVATE)
        .getBoolean("calendar_switch_enabled", false)

    fun setSwitchEnabled(context: Context, enabled: Boolean) = context
        .getSharedPreferences("alarm_prefs", MODE_PRIVATE)
        .edit { putBoolean("calendar_switch_enabled", enabled) }
}
