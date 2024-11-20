package net.micg.plantcare.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.Module

@Module
class AlarmNotificationService(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun cancelAlarm(id: Int) {
        alarmManager.cancel(createPendingIntent(id))
        Toast.makeText(context, "Будильник отменён", Toast.LENGTH_SHORT).show()
    }

    fun setAlarm(id: Int, triggerAtMillis: Long, intervalMillis: Long) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            createPendingIntent(id)
        )

        Toast.makeText(context, "Будильник установлен", Toast.LENGTH_SHORT).show()
    }

    private fun createPendingIntent(id: Int, alarmName: String, alarmType: String) =
        PendingIntent.getBroadcast(
            context,
            id,
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ALARM_NAME", alarmName)
                putExtra("ALARM_TYPE", alarmType)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}