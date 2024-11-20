package net.micg.plantcare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.AlarmNotificationService

@Module(includes = [
    AppBindsModule::class,
    ViewModelModule::class,
    DatabaseModule::class,
    AlarmNotificationService::class
])
class AppModule {
    @Provides
    fun provideAlarmNotificationService(context: Context): AlarmNotificationService {
        return AlarmNotificationService(context)
    }
}