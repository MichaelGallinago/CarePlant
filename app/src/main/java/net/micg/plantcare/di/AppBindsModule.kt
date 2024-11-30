package net.micg.plantcare.di

import dagger.Binds
import dagger.Module
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.alarm.AlarmsRepositoryImpl
import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.article.ArticlesRepositoryImpl
import net.micg.plantcare.data.article.LocalArticlesDataSource
import net.micg.plantcare.data.article.LocalArticlesDataSourceImpl
import net.micg.plantcare.data.article.RemoteArticlesDataSource
import net.micg.plantcare.data.article.RemoteArticlesDataSourceImpl
import net.micg.plantcare.domain.implementations.CancelAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.DeleteAlarmByIdUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllAlarmsUseCaseImpl
import net.micg.plantcare.domain.implementations.GetAllArticlesUseCaseImpl
import net.micg.plantcare.domain.implementations.GetErrorMessageUseCaseImpl
import net.micg.plantcare.domain.implementations.InsertAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.SetAlarmUseCaseImpl
import net.micg.plantcare.domain.implementations.UpdateAlarmUseCaseImpl
import net.micg.plantcare.domain.useCase.CancelAlarmUseCase
import net.micg.plantcare.domain.useCase.DeleteAlarmByIdUseCase
import net.micg.plantcare.domain.useCase.GetAllAlarmsUseCase
import net.micg.plantcare.domain.useCase.GetAllArticlesUseCase
import net.micg.plantcare.domain.useCase.GetErrorMessageUseCase
import net.micg.plantcare.domain.useCase.InsertAlarmUseCase
import net.micg.plantcare.domain.useCase.SetAlarmUseCase
import net.micg.plantcare.domain.useCase.UpdateAlarmUseCase

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
    fun bindGetErrorMessageUseCaseImpl(useCase: GetErrorMessageUseCaseImpl): GetErrorMessageUseCase

    @Binds
    fun bindInsertAlarmUseCase(useCase: InsertAlarmUseCaseImpl): InsertAlarmUseCase

    @Binds
    fun bindSetAlarmUseCase(useCase: SetAlarmUseCaseImpl): SetAlarmUseCase

    @Binds
    fun bindUpdateAlarmUseCase(useCase: UpdateAlarmUseCaseImpl): UpdateAlarmUseCase

    @Binds
    fun bindLocalArticlesDataSource(dataSource: LocalArticlesDataSourceImpl): LocalArticlesDataSource

    @Binds
    fun bindRemoteArticlesDataSource(dataSource: RemoteArticlesDataSourceImpl): RemoteArticlesDataSource
}
