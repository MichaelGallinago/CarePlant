package net.micg.plantcare.di

import dagger.Binds
import dagger.Module
import net.micg.plantcare.data.alarm.AlarmsRepository
import net.micg.plantcare.data.alarm.AlarmsRepositoryImpl
import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.data.article.ArticlesRepositoryImpl

@Module
interface AppBindsModule {
    @Binds
    fun bindAlarmsRepository(repository: AlarmsRepositoryImpl): AlarmsRepository

    @Binds
    fun bindArticlesRepository(repository: ArticlesRepositoryImpl): ArticlesRepository
}
