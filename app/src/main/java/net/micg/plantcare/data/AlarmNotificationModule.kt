package net.micg.plantcare.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import dagger.Module

@Module
class AlarmNotificationModule(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun cancelAlarm(id: Long) {
        alarmManager.cancel(createPendingIntent(id, "", ""))
        Toast.makeText(context, "Будильник отменён $id", Toast.LENGTH_SHORT).show() // TODO: remove
    }

    fun setAlarm(
        id: Long, name: String, type: String, triggerAtMillis: Long, intervalMillis: Long
    ) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            createPendingIntent(id, name, type)
        )

        Toast.makeText(context, "Будильник установлен $id", Toast.LENGTH_SHORT).show() // TODO: remove
    }

    private fun createPendingIntent(id: Long, name: String, type: String) =
        PendingIntent.getBroadcast(
            context,
            id.toInt(),
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ALARM_ID", id)
                putExtra("ALARM_NAME", name)
                putExtra("ALARM_TYPE", type)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}