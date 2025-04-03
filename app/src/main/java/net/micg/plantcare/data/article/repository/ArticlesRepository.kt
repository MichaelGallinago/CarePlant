package net.micg.plantcare.data.article.repository

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.data.article.models.Article

interface ArticlesRepository {
    suspend fun getAll(locale: String): HttpResponseState<List<Article>>
    suspend fun getAlarmCreationData(
        locale: String, fileName: String,
    ): HttpResponseState<AlarmCreationModel>
}
