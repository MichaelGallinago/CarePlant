package net.micg.plantcare.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [
    AppBindsModule::class,
    ViewModelModule::class,
    AlarmDatabaseModule::class,
    ArticleDatabaseModule::class,
    AlarmNotificationModule::class
])
class AppModule {
    @Provides
    fun provideAlarmNotificationService(context: Context): AlarmNotificationModule {
        return AlarmNotificationModule(context)
    }
}
