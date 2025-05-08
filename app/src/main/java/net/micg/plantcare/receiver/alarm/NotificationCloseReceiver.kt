package net.micg.plantcare.receiver.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.ID_EXTRA
import net.micg.plantcare.utils.FirebaseUtils.MARKED_PUSH_MESSAGES
import net.micg.plantcare.utils.FirebaseUtils.onNotificationEvent

class NotificationCloseReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context, intent: Intent
    ) = with(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager) {
        cancel(intent.getIntExtra(ID_EXTRA, 0))
        onNotificationEvent(context, MARKED_PUSH_MESSAGES, intent)
    }
}
