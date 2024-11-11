package net.micg.plantcare

import android.app.Application
import net.micg.plantcare.di.AppComponent
import net.micg.plantcare.di.DaggerAppComponent

class PlantCareApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        super.onCreate()
    }
}
