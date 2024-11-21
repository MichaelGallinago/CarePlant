package net.micg.plantcare.di

import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.domain.alarm.DeleteAlarmByIdUseCase
import net.micg.plantcare.domain.alarm.GetAllAlarmsUseCase
import net.micg.plantcare.domain.alarm.InsertAlarmUseCase
import net.micg.plantcare.domain.alarm.UpdateAlarmUseCase
import net.micg.plantcare.service.TypeLabelService

@Module
class AlarmUseCaseModule {
    @Provides
    fun provideGetAllAlarmsUseCase(repository: AlarmsRepository, service: TypeLabelService) =
        GetAllAlarmsUseCase(repository, service)

    @Provides
    fun provideUpdateAlarmUseCase(repository: AlarmsRepository) = UpdateAlarmUseCase(repository)

    @Provides
    fun provideInsertAlarmUseCase(repository: AlarmsRepository, service: TypeLabelService) =
        InsertAlarmUseCase(repository, service)

    @Provides
    fun provideDeleteAlarmUseCase(repository: AlarmsRepository) = DeleteAlarmByIdUseCase(repository)
}
