package net.micg.plantcare.di

import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.AlarmRepository
import net.micg.plantcare.presentation.AlarmViewModel

@Module
abstract class ViewModelModule {
    @Provides
    fun provideAlarmViewModel(repository: AlarmRepository): AlarmViewModel {
        return AlarmViewModel(repository)
    }
}