package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.alarm.AlarmCreationModel
import net.micg.plantcare.data.models.article.Article

interface RemoteArticlesDataSource {
    suspend fun getAll(): HttpResponseState<List<Article>>
    suspend fun getAlarmCreationData(fileName: String): HttpResponseState<AlarmCreationModel>
}
