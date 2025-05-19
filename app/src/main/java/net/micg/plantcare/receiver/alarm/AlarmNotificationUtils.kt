package net.micg.plantcare.receiver.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.provider.CalendarContract.Reminders
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import net.micg.plantcare.R
import net.micg.plantcare.presentation.MainActivity
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.ID_EXTRA
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.NAME_EXTRA
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.TYPE_EXTRA
import java.util.Objects
import java.util.TimeZone
import kotlin.math.max


object AlarmNotificationUtils {
    private const val HALF_MINUTE_IN_MILLIS = 1000L * 30L

    fun cancelAlarm(context: Context, id: Long) = getAlarmManager(context).cancel(
        createPendingIntent(context, id.toInt(), createIntent(context))
    )

    fun getValidDate(date: Long, interval: Long): Long {
        val currentTime = System.currentTimeMillis()

        if (interval == 0L) return max(date, currentTime + HALF_MINUTE_IN_MILLIS)
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
        addToCalendar: Boolean
    ) {
        getAlarmManager(context).setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dateInMillis,
            createPendingIntent(
                context,
                id,
                createExtraIntent(context, id, name, type, dateInMillis, intervalInMillis)
            )
        )

        if (!addToCalendar) return
        //insertCalendarEvent(context, name, type, dateInMillis)
        //insertEventAutomatically(context, name, type, dateInMillis, dateInMillis)
        addEventToCalendar(context)
    }

    fun getGroupSummaryNotification(
        context: Context, complete: String, title: String
    ) = NotificationCompat.Builder(context, AlarmReceiver.Companion.ALARM_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_alarm)
        .setContentTitle(title)
        .setContentText(complete)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setOngoing(true)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .setContentIntent(getActivity(context, 0, Intent(), FLAG_IMMUTABLE))
        .setGroup(AlarmReceiver.Companion.ALARM_GROUP)
        .setGroupSummary(true)
        .build()

    fun createContentIntent(context: Context, id: Int): PendingIntent? = getActivity(
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

    fun createDeleteIntent(context: Context, id: Int, name: String, type: String): PendingIntent? =
        getBroadcast(
            context,
            id,
            Intent(context, NotificationDismissReceiver::class.java).apply {
                putExtra(ID_EXTRA, id)
                putExtra(NAME_EXTRA, name)
                putExtra(TYPE_EXTRA, type)
            },
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

    fun insertEventAutomatically(context: Context, title: String, description: String, startMillis: Long) {
        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)
        val uri = CalendarContract.Calendars.CONTENT_URI
        val cursor = context.contentResolver.query(uri, projection, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            val calId = cursor.getLong(0)

            val values = ContentValues().apply {
                put(Events.DTSTART, startMillis)
                put(Events.DTEND, startMillis)
                put(Events.TITLE, title)
                put(Events.DESCRIPTION, description)
                put(Events.CALENDAR_ID, calId)
                put(Events.EVENT_TIMEZONE, TimeZone.getDefault().id)

            }

            val eventUri = context.contentResolver.insert(Events.CONTENT_URI, values)
            Log.d("CalendarCalendar", "Событие создано: $eventUri")
            val eventId = ContentUris.parseId(eventUri ?: return)

            context.contentResolver.insert(Reminders.CONTENT_URI, ContentValues().apply {
                put(Reminders.EVENT_ID, eventId)
                put(Reminders.MINUTES, 0)
                put(Reminders.METHOD, Reminders.METHOD_ALERT)
            })

            cursor.close()
        } else {
            Log.e("CalendarCalendar", "Не удалось получить доступный календарь")
        }
    }

    fun addEventToCalendar(context: Context) {


        val time = Calendar.getInstance().timeInMillis + 60000
        insertEventAutomatically(context, "Цветок", "Полив", time)
        Log.d("MYDEBUG", time.toString())
    }
}
