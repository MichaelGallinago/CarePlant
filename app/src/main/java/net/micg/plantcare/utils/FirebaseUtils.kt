package net.micg.plantcare.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.ID_EXTRA
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.NAME_EXTRA
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.TYPE_EXTRA

object FirebaseUtils {
    fun logEvent(context: Context, eventName: String, bundle: Bundle = Bundle())
        = FirebaseAnalytics.getInstance(context).logEvent(eventName, bundle.apply {
            putString(USER_UUID, UUIDUtils.getDeviceUUID(context))
        })

    fun onNotificationDismissed(context: Context, intent: Intent) =
        logEvent(context, MARKED_PUSH_MESSAGES, Bundle().apply {
            putInt("id", intent.getIntExtra(ID_EXTRA, 0))
            putString("name", intent.getStringExtra(NAME_EXTRA))
            putString("type", intent.getStringExtra(TYPE_EXTRA))
        })

    private const val USER_UUID = "user_uuid"

    const val MARKED_PUSH_MESSAGES = "marked_push_messages"
    const val CREATED_NOTIFICATIONS = "created_notifications"

    const val ALARMS_ENTERS = "alarms_enters"
    const val ARTICLE_ENTERS = "article_enters"
    const val ALARM_CREATION_ENTERS = "alarm_creation_enters"
    const val ARTICLE_READ_DURATION = "article_read_duration"
}
