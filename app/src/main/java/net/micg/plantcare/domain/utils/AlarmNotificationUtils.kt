package net.micg.plantcare.domain.utils

import android.app.AlarmManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import net.micg.plantcare.AlarmReceiver
import net.micg.plantcare.presentation.models.Alarm

object AlarmNotificationUtils {
    fun cancelAlarm(context: Context, id: Long) =
        AlarmManagerUtils.getManager(context)?.cancel(createPendingIntent(context, id, "", ""))

    fun setAlarm(context: Context, alarm: Alarm) = with(alarm) {
        AlarmManagerUtils.getManager(context)?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            intervalInMillis,
            createPendingIntent(context, id, name, type)
        )
    }

    private fun createPendingIntent(context: Context, id: Long, name: String, type: String) =
        getBroadcast(
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
