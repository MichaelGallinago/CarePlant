package net.micg.plantcare.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import net.micg.plantcare.R
import net.micg.plantcare.utils.FirebaseUtils

class PlantCareFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        FirebaseUtils.logEvent(applicationContext, GET_FMC_TOKEN, Bundle().apply {
            putString(FMC_TOKEN, token)
        })
        Log.d("FCM", "New token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data["url"]?.let { url ->
            startActivity(Intent(Intent.ACTION_VIEW, url.toUri()).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        Log.d("FCM", "message received")
    }

    companion object {
        const val TOPIC_ALL_DEVICES = "allDevices"

        private const val GET_FMC_TOKEN = "get_fms_token"
        private const val FMC_TOKEN = "fms_token"
    }
}
