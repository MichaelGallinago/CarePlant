package net.micg.plantcare.domain.utils

import android.app.AlarmManager
import android.content.Context

object AlarmManagerUtils {
    private lateinit var _alarmManager: AlarmManager
    val alarmManager get() = _alarmManager

    fun init(context: Context) {
        _alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
}
