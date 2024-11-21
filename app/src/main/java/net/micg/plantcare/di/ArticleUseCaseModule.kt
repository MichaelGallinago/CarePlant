package net.micg.plantcare.di

import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.article.ArticlesRepository
import net.micg.plantcare.domain.article.GetAllArticlesUseCase
import net.micg.plantcare.domain.article.LoadArticlesUseCase
import net.micg.plantcare.domain.article.SaveCurrentArticlesUseCase

@Module
class ArticleUseCaseModule {
    @Provides
    fun provideGetAllArticlesUseCase(repository: ArticlesRepository): GetAllArticlesUseCase =
        GetAllArticlesUseCase(repository)

    @Provides
    fun provideSaveCurrentArticlesUseCase(repository: ArticlesRepository) =
        SaveCurrentArticlesUseCase(repository)

    @Provides
    fun provideLoadArticlesUseCase(): LoadArticlesUseCase = LoadArticlesUseCase()
}
