package net.micg.plantcare.di

import dagger.Module

@Module(
    includes = [
        AppBindsModule::class,
        ViewModelModule::class,
        AlarmDatabaseModule::class,
        ArticleDatabaseModule::class
    ]
)
class AppModule
