package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.HttpResponseState
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalArticlesDataSource,
    private val remoteDataSource: RemoteArticlesDataSource,
) : ArticlesRepository {
    override suspend fun getAll() = remoteDataSource.getAll().apply {
        if (this is HttpResponseState.Success) return@apply

        with(localDataSource.getAll()) {
            if (isEmpty()) return@apply
            localDataSource.clear()
            localDataSource.insertAll(this)
            return HttpResponseState.Success(this)
        }
    }
}
