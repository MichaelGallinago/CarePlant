package net.micg.plantcare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import net.micg.plantcare.di.AppComponent
import net.micg.plantcare.di.DaggerAppComponent
import net.micg.plantcare.receiver.alarm.AlarmReceiver.Companion.ALARM_CHANNEL_ID
import net.micg.plantcare.service.PlantCareFirebaseMessagingService.Companion.TOPIC_ALL_DEVICES

class PlantCareApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        createNotificationChannel()
        setupFirebaseMessaging()
        super.onCreate()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        with(getSystemService(NOTIFICATION_SERVICE) as NotificationManager) {
            createNotificationChannel(
                NotificationChannel(
                    ALARM_CHANNEL_ID, "Alarm", NotificationManager.IMPORTANCE_HIGH
                ).apply { description = "Used for the plant alarm notifications" }
            )
        }
    }

    private fun setupFirebaseMessaging() = Firebase.messaging.subscribeToTopic(TOPIC_ALL_DEVICES)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Subscribed to topic")
            } else {
                Log.e("FCM", "Subscription failed", task.exception)
            }
        }
}
