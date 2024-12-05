package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.alarm.AlarmCreationModel
import net.micg.plantcare.data.models.article.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("articles.json")
    fun getArticles(): Call<List<Article>>

    @GET("alarmData/{fileName}")
    fun getAlarmCreationData(@Path("fileName") fileName: String): Call<AlarmCreationModel>
}
