package net.micg.plantcare.di

import dagger.Module

@Module(includes = [
    AppBindsModule::class,
    ViewModelModule::class,
    AlarmDatabaseModule::class,
    ArticleDatabaseModule::class,
    ServiceModule::class,
    ArticleUseCaseModule::class,
    AlarmUseCaseModule::class
])
class AppModule
