package net.micg.plantcare.data.article.datasource.remote

import net.micg.plantcare.data.article.api.ArticlesApi
import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.alarm.models.AlarmCreationModel
import net.micg.plantcare.data.article.models.Article
import net.micg.plantcare.utils.ErrorMessageUtils
import retrofit2.awaitResponse
import javax.inject.Inject

class RemoteArticlesDataSourceImpl @Inject constructor(
    private val api: ArticlesApi,
) : RemoteArticlesDataSource {
    override suspend fun getAll(): HttpResponseState<List<Article>> = runCatching {
        api.getArticles().awaitResponse()
    }.fold(
        onSuccess = { response ->
            if (!response.isSuccessful) return HttpResponseState.Failure(
                response.message(), ErrorMessageUtils.Type.LoadingError
            )

            return HttpResponseState.Success(response.body()?.toList() ?: emptyList())
        },
        onFailure = { throwable -> onFailure(throwable) }
    )

    override suspend fun getAlarmCreationData(
        fileName: String,
    ): HttpResponseState<AlarmCreationModel> = runCatching {
        api.getAlarmCreationData(fileName).awaitResponse()
    }.fold(
        onSuccess = { response ->
            if (!response.isSuccessful) return HttpResponseState.Failure(
                response.message(), ErrorMessageUtils.Type.LoadingError
            )

            return HttpResponseState.Success(response.body() ?: AlarmCreationModel("", 1))
        },
        onFailure = { throwable -> onFailure(throwable) }
    )

    private fun onFailure(throwable: Throwable) = HttpResponseState.Failure(
        throwable.message ?: "failure", ErrorMessageUtils.Type.LoadingError
    )
}

