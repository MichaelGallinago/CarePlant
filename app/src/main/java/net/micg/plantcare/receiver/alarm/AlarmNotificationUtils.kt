package net.micg.plantcare.receiver.alarm

import android.app.AlarmManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import net.micg.plantcare.R
import net.micg.plantcare.presentation.MainActivity

object AlarmNotificationUtils {
    private const val HALF_MINUTE_IN_MILLIS = 1000L * 30L

    fun cancelAlarm(context: Context, id: Long) = getAlarmManager(context).cancel(
        createPendingIntent(context, id.toInt(), createIntent(context))
    )

    fun getValidDate(date: Long, interval: Long): Long {
        val currentTime = System.currentTimeMillis()
        if (date >= currentTime - HALF_MINUTE_IN_MILLIS) return date

        return date + ((currentTime - date) / interval + 1L) * interval
    }

    fun setAlarm(
        context: Context,
        id: Int,
        name: String,
        type: String,
        dateInMillis: Long,
        intervalInMillis: Long,
    ) = getAlarmManager(context).setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        dateInMillis,
        createPendingIntent(
            context,
            id,
            createExtraIntent(context, id, name, type, dateInMillis, intervalInMillis)
        )
    )

    fun getGroupSummaryNotification(
        context: Context, complete: String
    ) = NotificationCompat.Builder(context, AlarmReceiver.Companion.ALARM_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_alarm)
        .setContentTitle(complete)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setOngoing(true)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .setContentIntent(getActivity(context, 0, Intent(), FLAG_IMMUTABLE))
        .setGroup(AlarmReceiver.Companion.ALARM_GROUP)
        .setGroupSummary(true)
        .build()

    fun createContentIntent(context: Context, id: Int) = getActivity(
        context,
        id,
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(
                AlarmReceiver.Companion.FRAGMENT_TAG,
                AlarmReceiver.Companion.ALARMS_FRAGMENT_TAG
            )
        },
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    private fun createPendingIntent(context: Context, id: Int, intent: Intent) = getBroadcast(
        context,
        id,
        intent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    private fun createIntent(context: Context) = Intent(context, AlarmReceiver::class.java)

    private fun createExtraIntent(
        context: Context,
        id: Int,
        name: String,
        type: String,
        dateInMillis: Long,
        intervalInMillis: Long,
    ) = createIntent(context).apply {
        putExtra(AlarmReceiver.Companion.ALARM_ID, id)
        putExtra(AlarmReceiver.Companion.ALARM_NAME, name)
        putExtra(AlarmReceiver.Companion.ALARM_TYPE, type)
        putExtra(AlarmReceiver.Companion.ALARM_DATE, dateInMillis)
        putExtra(AlarmReceiver.Companion.ALARM_INTERVAL, intervalInMillis)
    }

    private fun getAlarmManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
}
