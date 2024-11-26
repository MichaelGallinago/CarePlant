package net.micg.plantcare

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import net.micg.plantcare.AlarmReceiver.Companion.ALARM_CHANNEL_ID
import net.micg.plantcare.di.AppComponent
import net.micg.plantcare.di.DaggerAppComponent
import net.micg.plantcare.domain.utils.AlarmManagerUtils

class PlantCareApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        AlarmManagerUtils.init(applicationContext)
        createNotificationChannel()
        super.onCreate()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        with(getSystemService(NOTIFICATION_SERVICE) as NotificationManager) {
            createNotificationChannel(NotificationChannel(
                ALARM_CHANNEL_ID, "Alarm", NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Used for the plant alarm notifications" })
        }
    }
}
