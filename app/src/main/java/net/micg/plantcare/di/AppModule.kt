package net.micg.plantcare.di

import dagger.Module
import net.micg.plantcare.di.viewModel.ViewModelModule

@Module(
    includes = [
        AppBindsModule::class,
        ViewModelModule::class,
        AlarmDatabaseModule::class,
        ArticleDatabaseModule::class,
        NetworkModule::class
    ]
)
class AppModule
