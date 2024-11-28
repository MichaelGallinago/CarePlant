package net.micg.plantcare

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import net.micg.plantcare.domain.utils.AlarmNotificationUtils
import net.micg.plantcare.presentation.MainActivity
import net.micg.plantcare.presentation.utils.AlarmCreationUtils

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ALARM_ID, 0)
        Log.d("alarm_debug", id.toString())

        val name = intent.getStringExtra(ALARM_NAME).takeUnless {
            it.isNullOrBlank()
        } ?: context.getString(R.string.notification_name_placeholder)

        val type = intent.getStringExtra(ALARM_TYPE).takeUnless {
            it.isNullOrBlank()
        } ?: context.getString(R.string.notification_type_placeholder)

        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
            id,
            NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_flower)
                .setContentTitle(name)
                .setContentText(type)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).build()
        )

        val dateInMillis = intent.getLongExtra(ALARM_DATE, System.currentTimeMillis())

        val intervalInMillis =
            intent.getLongExtra(ALARM_INTERVAL, AlarmCreationUtils.calculateIntervalInMillis(1L))

        AlarmNotificationUtils.setAlarm(
            context, id, name, type, dateInMillis + intervalInMillis, intervalInMillis
        )
    }

    companion object {
        const val ALARM_CHANNEL_ID = "flowers_alarm_channel_id"
        const val ALARM_ID = "ALARM_ID"
        const val ALARM_NAME = "ALARM_NAME"
        const val ALARM_TYPE = "ALARM_TYPE"
        const val ALARM_DATE = "ALARM_DATE"
        const val ALARM_INTERVAL = "ALARM_INTERVAL"
    }
}
