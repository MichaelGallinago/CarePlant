package net.micg.plantcare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.AlarmNotificationModule

@Module(includes = [
    AppBindsModule::class,
    ViewModelModule::class,
    DatabaseModule::class,
    AlarmNotificationModule::class
])
class AppModule {
    @Provides
    fun provideAlarmNotificationService(context: Context): AlarmNotificationModule {
        return AlarmNotificationModule(context)
    }
}