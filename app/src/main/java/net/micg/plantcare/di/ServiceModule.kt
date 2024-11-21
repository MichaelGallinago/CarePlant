package net.micg.plantcare.di

import android.content.Context
import dagger.Module
import dagger.Provides
import net.micg.plantcare.service.AlarmNotificationService
import net.micg.plantcare.service.ErrorMessageService
import net.micg.plantcare.service.TypeLabelService

@Module
class ServiceModule {
    @Provides
    fun provideAlarmNotificationService(context: Context) = AlarmNotificationService(context)

    @Provides
    fun provideTypeLabelService(context: Context) = TypeLabelService(context)

    @Provides
    fun provideErrorMessageService(context: Context) = ErrorMessageService(context)
}
