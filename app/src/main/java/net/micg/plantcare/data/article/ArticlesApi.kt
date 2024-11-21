package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.article.Article
import retrofit2.Call
import retrofit2.http.GET

interface ArticlesApi {
    @GET("articles.json")
    fun getArticles(): Call<List<Article>>
}
