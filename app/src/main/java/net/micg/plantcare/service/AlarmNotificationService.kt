package net.micg.plantcare.service

import android.app.AlarmManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import net.micg.plantcare.AlarmReceiver
import net.micg.plantcare.presentation.models.Alarm

class AlarmNotificationService(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun cancelAlarm(id: Long) = alarmManager.cancel(createPendingIntent(id, "", ""))

    fun setAlarm(alarm: Alarm) = with(alarm) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            intervalInMillis,
            createPendingIntent(id, name, type)
        )
    }

    private fun createPendingIntent(id: Long, name: String, type: String) = getBroadcast(
        context,
        id.toInt(),
        Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.ALARM_ID, id)
            putExtra(AlarmReceiver.ALARM_NAME, name)
            putExtra(AlarmReceiver.ALARM_TYPE, type)
        },
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )
}
