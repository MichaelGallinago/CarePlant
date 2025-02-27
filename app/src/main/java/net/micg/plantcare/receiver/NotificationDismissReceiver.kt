package net.micg.plantcare.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.micg.plantcare.receiver.AlarmReceiver.Companion.ID_EXTRA

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var id = intent.getIntExtra(ID_EXTRA, 0)
        //TODO: send analytics
    }
}