package net.micg.plantcare.receiver.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.micg.plantcare.utils.FirebaseUtils.MARKED_PUSH_MESSAGES
import net.micg.plantcare.utils.FirebaseUtils.onNotificationEvent

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) =
        onNotificationEvent(context, MARKED_PUSH_MESSAGES, intent)
}
