package net.micg.plantcare.data.article.datasource.remote

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.data.article.models.Article

interface RemoteArticlesDataSource {
    suspend fun getAll(): HttpResponseState<List<Article>>
    suspend fun getAlarmCreationData(fileName: String): HttpResponseState<AlarmCreationModel>
}
