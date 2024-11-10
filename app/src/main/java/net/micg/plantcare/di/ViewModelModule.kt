package net.micg.plantcare.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import net.micg.plantcare.presentation.AlarmViewModel

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel::class)
    fun bindAlarmViewModel(vm: AlarmViewModel): ViewModel
}
