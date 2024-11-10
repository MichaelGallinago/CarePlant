package net.micg.plantcare.di

import dagger.Binds
import dagger.Module
import net.micg.plantcare.data.AlarmsRepository
import net.micg.plantcare.data.AlarmsRepositoryImpl

@Module
interface AppBindsModule {
    @Binds
    fun bindAlarmsRepository(repository: AlarmsRepositoryImpl): AlarmsRepository
}