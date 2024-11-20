package net.micg.plantcare.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import net.micg.plantcare.R
import net.micg.plantcare.presentation.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val alarmId = intent.getIntExtra("ALARM_ID", 0)
        val pendingIntent = PendingIntent.getActivity(
            context,
            alarmId,
            i,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmName = intent.getStringExtra("ALARM_NAME").takeUnless {
            it.isNullOrBlank()
        } ?: "Уход за растением"
        val alarmType = intent.getStringExtra("ALARM_TYPE").takeUnless {
            it.isNullOrBlank()
        } ?: "Ваше растение нуждается в вас"

        val notification =
            NotificationCompat.Builder(context, ALARM_CHANNEL_ID).setSmallIcon(R.drawable.ic_flower)
                .setContentTitle(alarmName).setContentText(alarmType)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
                .setAutoCancel(true).build()

        with(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
            notify(alarmId, notification)
        }
    }

    companion object {
        const val ALARM_CHANNEL_ID = "flowers_alarm_channel_id"
    }
}
