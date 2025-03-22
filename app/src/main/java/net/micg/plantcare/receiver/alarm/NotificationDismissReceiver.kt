package net.micg.plantcare.receiver.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import net.micg.plantcare.utils.FirebaseUtils

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) =
        FirebaseUtils.logEvent(context, FirebaseUtils.MARKED_PUSH_MESSAGES, Bundle().apply {
            putInt("id", intent.getIntExtra(AlarmReceiver.Companion.ID_EXTRA, 0))
            putString("name", intent.getStringExtra(AlarmReceiver.Companion.NAME_EXTRA))
            putString("type", intent.getStringExtra(AlarmReceiver.Companion.TYPE_EXTRA))
        })
}
