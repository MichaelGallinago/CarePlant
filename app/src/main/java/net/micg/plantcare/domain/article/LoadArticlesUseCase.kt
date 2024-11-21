package net.micg.plantcare.domain.article

import net.micg.plantcare.BuildConfig
import net.micg.plantcare.data.article.ArticlesApi
import net.micg.plantcare.data.models.article.Article
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoadArticlesUseCase {
    private val api =
        Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ArticlesApi::class.java)

    operator fun invoke(callback: Callback<List<Article>>) = api.getArticles().enqueue(callback)
}
