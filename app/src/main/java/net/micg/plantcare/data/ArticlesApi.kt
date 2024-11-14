package net.micg.plantcare.data

import net.micg.plantcare.data.models.Article
import retrofit2.Call
import retrofit2.http.GET

interface ArticlesApi {
    @GET("articles.json")
    fun getArticles(): Call<List<Article>>
}
