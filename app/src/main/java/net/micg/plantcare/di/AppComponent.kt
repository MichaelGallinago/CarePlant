package net.micg.plantcare.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.micg.plantcare.presentation.fragments.AlarmsFragment

@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: AlarmsFragment)
}