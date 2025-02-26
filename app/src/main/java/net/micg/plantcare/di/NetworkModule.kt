package net.micg.plantcare.di

import dagger.Module
import dagger.Provides
import net.micg.plantcare.BuildConfig
import net.micg.plantcare.data.article.api.ArticlesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    @AppComponentScope
    fun provideArticlesApi(): ArticlesApi = Retrofit.Builder().baseUrl(BuildConfig.WEB_STORAGE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(ArticlesApi::class.java)
}
