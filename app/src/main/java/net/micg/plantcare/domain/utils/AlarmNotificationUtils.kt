package net.micg.plantcare.domain.utils

import android.app.AlarmManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import net.micg.plantcare.AlarmReceiver
import net.micg.plantcare.presentation.models.Alarm

object AlarmNotificationUtils {
    fun cancelAlarm(context: Context, id: Long) = AlarmManagerUtils.alarmManager.cancel(
        createPendingIntent(context, id, createIntent(context))
    )

    fun setAlarm(context: Context, alarm: Alarm) = with(alarm) {
        AlarmManagerUtils.alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            intervalInMillis,
            createPendingIntent(context, id, createExtraIntent(context, id, name, type))
        )
    }

    private fun createPendingIntent(context: Context, id: Long, intent: Intent) = getBroadcast(
        context,
        id.toInt(),
        intent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
    )

    private fun createIntent(context: Context) = Intent(context, AlarmReceiver::class.java)

    private fun createExtraIntent(
        context: Context, id: Long, name: String, type: String,
    ) = createIntent(context).apply {
        putExtra(AlarmReceiver.ALARM_ID, id)
        putExtra(AlarmReceiver.ALARM_NAME, name)
        putExtra(AlarmReceiver.ALARM_TYPE, type)
    }
}
