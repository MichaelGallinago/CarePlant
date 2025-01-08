package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.HttpResponseState
import net.micg.plantcare.data.models.alarm.AlarmCreationModel
import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.domain.utils.ErrorMessageUtils
import retrofit2.awaitResponse
import javax.inject.Inject

class RemoteArticlesDataSourceImpl @Inject constructor(
    private val api: ArticlesApi,
) : RemoteArticlesDataSource {
    override suspend fun getAll(): HttpResponseState<List<Article>> = kotlin.runCatching {
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
    ): HttpResponseState<AlarmCreationModel> = kotlin.runCatching {
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

