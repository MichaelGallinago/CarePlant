package net.micg.plantcare.data.article.api

import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.data.article.models.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("articles.json")
    fun getArticles(): Call<List<Article>>

    @GET("alarmData/{fileName}")
    fun getAlarmCreationData(@Path("fileName") fileName: String): Call<AlarmCreationModel>
}
