package net.micg.plantcare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import net.micg.plantcare.AlarmReceiver.Companion.ALARM_CHANNEL_ID
import net.micg.plantcare.di.AppComponent
import net.micg.plantcare.di.DaggerAppComponent

class PlantCareApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        createNotificationChannel()
        super.onCreate()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            ALARM_CHANNEL_ID,
            "Alarm",
            NotificationManager.IMPORTANCE_HIGH
        ).apply { description = "Used for the plant alarm notifications" }

        with(getSystemService(NOTIFICATION_SERVICE) as NotificationManager) {
            createNotificationChannel(channel)
        }
    }
}
