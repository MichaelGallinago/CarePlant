package net.micg.plantcare.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import net.micg.plantcare.R
import net.micg.plantcare.utils.AlarmCreationUtils

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ALARM_ID, 0)

        val name = getString(context, intent, ALARM_NAME, R.string.notification_name_placeholder)
        val type = getString(context, intent, ALARM_TYPE, R.string.notification_type_placeholder)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        when (notificationManager.activeNotifications.count()) {
            0 -> notificationManager.notify(
                id,
                NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_flower)
                    .setContentTitle(name)
                    .setContentText(type)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(AlarmNotificationUtils.createContentIntent(context, id))
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .setGroup(ALARM_GROUP)
                    .setDeleteIntent(createDeleteIntent(context, id))
                    .build()
            )
            1 -> {
                val existsNotification = notificationManager.activeNotifications[0]
                val extras = existsNotification.notification.extras
                notifyAlarm(
                    context,
                    notificationManager,
                    existsNotification.id,
                    extras.getString(Notification.EXTRA_TITLE) ?: "",
                    extras.getString(Notification.EXTRA_TEXT) ?: ""
                )
                notifyAlarm(context, notificationManager, id, name, type)
                notifyGroupSummary(context, notificationManager)
            }
            else -> {
                notifyAlarm(context, notificationManager, id, name, type)
                notifyGroupSummary(context, notificationManager)
            }
        }

        val dateInMillis = intent.getLongExtra(ALARM_DATE, System.currentTimeMillis())

        val intervalInMillis =
            intent.getLongExtra(ALARM_INTERVAL, AlarmCreationUtils.calculateIntervalInMillis(1L))

        AlarmNotificationUtils.setAlarm(
            context, id, name, type, dateInMillis + intervalInMillis, intervalInMillis
        )
    }

    private fun notifyAlarm(
        context: Context,
        notificationManager: NotificationManager,
        id: Int,
        name: String,
        type: String
    ) = notificationManager.notify(
        id,
        NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_flower)
            .setContentTitle(name)
            .setContentText(type)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(AlarmNotificationUtils.createContentIntent(context, id))
            .setAutoCancel(false)
            .setGroup(ALARM_GROUP)
            .setDeleteIntent(createDeleteIntent(context, id))
            .build()
    )

    private fun notifyGroupSummary(
        context: Context, notificationManager: NotificationManager
    ) = Handler(Looper.getMainLooper()).postDelayed({
        notificationManager.notify(
            GROUP_SUMMARY_ID,
            AlarmNotificationUtils.getGroupSummaryNotification(
                context,
                context.getString(R.string.complete)
            )
        )
    }, GROUP_SUMMARY_DELAY)

    private fun getString(context: Context, intent: Intent, name: String, resId: Int) =
        intent.getStringExtra(name).takeUnless { it.isNullOrBlank() } ?: context.getString(resId)

    private fun createDeleteIntent(context: Context, id: Int) = PendingIntent.getBroadcast(
        context,
        id,
        Intent(context, NotificationDismissReceiver::class.java).apply {
            putExtra(ID_EXTRA, id)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    companion object {
        const val ID_EXTRA = "id"

        const val FRAGMENT_TAG = "fragment_tag"
        const val ALARMS_FRAGMENT_TAG = "fragment_tag"

        const val ALARM_CHANNEL_ID = "flowers_alarm_channel_id"
        const val ALARM_ID = "ALARM_ID"
        const val ALARM_NAME = "ALARM_NAME"
        const val ALARM_TYPE = "ALARM_TYPE"
        const val ALARM_DATE = "ALARM_DATE"
        const val ALARM_INTERVAL = "ALARM_INTERVAL"
        const val ALARM_GROUP = "ALARM_GROUP"

        const val GROUP_SUMMARY_DELAY = 400L
        const val GROUP_SUMMARY_ID = Int.MIN_VALUE
    }
}
