package net.micg.plantcare.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.alarm.AlarmsRepositoryImpl
import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.article.ArticlesRepositoryImpl
import net.micg.plantcare.domain.implementations.CancelAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.DeleteAlarmByIdUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllAlarmsUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.GetErrorMessageUseCaseImpl
import net.micg.plantcare.domain.implementations.InsertAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.LoadArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.SaveCurrentArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.SetAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.UpdateAlarmUseCaseImpl
import net.micg.plantcare.domain.usecase.CancelAlarmUseCase
import net.micg.plantcare.domain.usecase.DeleteAlarmByIdUseCase
import net.micg.plantcare.domain.usecase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.usecase.GetAllArticlesUseCase
import net.micg.plantcare.domain.usecase.InsertAlarmUseCase
import net.micg.plantcare.domain.usecase.LoadArticlesUseCase
import net.micg.plantcare.domain.usecase.SaveCurrentArticlesUseCase
import net.micg.plantcare.domain.usecase.SetAlarmUseCase
import net.micg.plantcare.domain.usecase.UpdateAlarmUseCase
import net.micg.plantcare.domain.utils.TypeLabelUtils

@Module
interface AppBindsModule {
    @Binds
    fun bindAlarmsRepository(repository: AlarmsRepositoryImpl): AlarmsRepository

    @Binds
    fun bindArticlesRepository(repository: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    fun bindCancelAlarmUseCase(useCase: CancelAlarmUseCaseImpl): CancelAlarmUseCase

    @Binds
    fun bindDeleteAlarmByIdUseCase(useCase: DeleteAlarmByIdUseCaseImpl): DeleteAlarmByIdUseCase

    @Binds
    fun bindGetAllAlarmsUseCase(useCase: GetAllAlarmsUseCaseImpl): GetAllAlarmsUseCase

    @Binds
    fun bindGetAllArticlesUseCase(useCase: GetAllArticlesUseCaseImpl): GetAllArticlesUseCase

    @Binds
    fun bindGetErrorMessageUseCaseImpl(useCase: GetErrorMessageUseCaseImpl): GetErrorMessageUseCaseImpl

    @Binds
    fun bindCancelAlarmUseCase(useCase: InsertAlarmUseCaseImpl): InsertAlarmUseCase

    @Binds
    fun bindCancelAlarmUseCase(useCase: LoadArticlesUseCaseImpl): LoadArticlesUseCase

    @Binds
    fun bindCancelAlarmUseCase(useCase: SaveCurrentArticlesUseCaseImpl): SaveCurrentArticlesUseCase

    @Binds
    fun bindCancelAlarmUseCase(useCase: SetAlarmUseCaseImpl): SetAlarmUseCase

    @Binds
    fun bindCancelAlarmUseCase(useCase: UpdateAlarmUseCaseImpl): UpdateAlarmUseCase

    /*@Provides
    fun provideGetAllArticlesUseCase(repository: ArticlesRepository): GetAllArticlesUseCase =
        GetAllArticlesUseCaseImpl(repository)

    @Provides
    fun provideSaveCurrentArticlesUseCase(repository: ArticlesRepository) =
        SaveCurrentArticlesUseCaseImpl(repository)*/

    /*@Provides
    fun provideGetAllAlarmsUseCase(repository: AlarmsRepository, service: TypeLabelUtils) =
        GetAllAlarmsUseCaseImpl(repository, service)

    @Provides
    fun provideUpdateAlarmUseCase(repository: AlarmsRepository) = UpdateAlarmUseCaseImpl(repository)

    @Provides
    fun provideInsertAlarmUseCase(repository: AlarmsRepository, service: TypeLabelUtils) =
        InsertAlarmUseCaseImpl(repository, service)

    @Provides
    fun provideDeleteAlarmUseCase(repository: AlarmsRepository) =
        DeleteAlarmByIdUseCaseImpl(repository)*/
}
