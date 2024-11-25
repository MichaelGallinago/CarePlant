package net.micg.plantcare.domain.utils

import android.app.AlarmManager
import android.content.Context

object AlarmManagerUtils {
    private var alarmManagerField: AlarmManager? = null

    fun getManager(context: Context): AlarmManager? {
        if (alarmManagerField != null) return alarmManagerField
        alarmManagerField = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return alarmManagerField
    }
}
