package net.micg.plantcare

import android.app.Application
import net.micg.plantcare.di.AppComponent

class PlantCareApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        //appComponent = DaggerAppComponent.create()

        super.onCreate()
    }
}