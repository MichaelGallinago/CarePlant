package net.micg.plantcare.data.article.api

import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.data.article.models.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticlesApi {
    @GET("data/list/{locale}/articles.json")
    fun getArticles(@Path("locale") locale: String): Call<List<Article>>

    @GET("data/alarmData/{locale}/{fileName}")
    fun getAlarmCreationData(
        @Path("locale") locale: String,
        @Path("fileName") fileName: String
    ): Call<AlarmCreationModel>
}
