package net.micg.plantcare.di

import dagger.Module

@Module(includes = [AppBindsModule::class, ViewModelModule::class, DatabaseModule::class])
class AppModule