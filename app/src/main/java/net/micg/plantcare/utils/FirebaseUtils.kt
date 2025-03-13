package net.micg.plantcare.utils

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FirebaseUtils {
    fun logEvent(context: Context, eventName: String, bundle: Bundle = Bundle())
        = FirebaseAnalytics.getInstance(context).logEvent(eventName, bundle.apply {
            putString(USER_UUID, UUIDUtils.getDeviceUUID(context))
        })

    private const val USER_UUID = "user_uuid"

    const val MARKED_PUSH_MESSAGES = "marked_push_messages"
    const val CREATED_NOTIFICATIONS = "created_notifications"

    const val ALARMS_ENTERS = "alarms_enters"
    const val ARTICLE_ENTERS = "article_enters"
    const val ALARM_CREATION_ENTERS = "alarm_creation_enters"
}
