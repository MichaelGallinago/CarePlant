package net.micg.plantcare.di

import android.content.Context
import net.micg.plantcare.PlantCareApplication

val Context.appComponent: AppComponent
    get() = when(this) {
        is PlantCareApplication -> appComponent
        else -> applicationContext.appComponent
    }