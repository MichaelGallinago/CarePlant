package net.micg.plantcare.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import net.micg.plantcare.receiver.AlarmReceiver.Companion.ID_EXTRA
import net.micg.plantcare.receiver.AlarmReceiver.Companion.NAME_EXTRA
import net.micg.plantcare.receiver.AlarmReceiver.Companion.TYPE_EXTRA
import net.micg.plantcare.utils.FirebaseUtils

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) =
        FirebaseUtils.logEvent(context, FirebaseUtils.MARKED_PUSH_MESSAGES, Bundle().apply {
            putInt("id", intent.getIntExtra(ID_EXTRA, 0))
            putString("name", intent.getStringExtra(NAME_EXTRA))
            putString("type", intent.getStringExtra(TYPE_EXTRA))
        })
}
