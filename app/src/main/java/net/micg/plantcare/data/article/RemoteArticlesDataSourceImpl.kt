package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.HttpResponseState
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
        onFailure = { throwable ->
            return HttpResponseState.Failure(
                throwable.message ?: "failure", ErrorMessageUtils.Type.LoadingError
            )
        }
    )
}
