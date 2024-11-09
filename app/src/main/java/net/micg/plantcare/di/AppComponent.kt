package net.micg.plantcare.di

import dagger.Component
import net.micg.plantcare.presentation.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}