package net.micg.plantcare.data.article.repository

import net.micg.plantcare.data.article.datasource.local.LocalArticlesDataSource
import net.micg.plantcare.data.article.datasource.remote.RemoteArticlesDataSource
import net.micg.plantcare.data.models.HttpResponseState
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalArticlesDataSource,
    private val remoteDataSource: RemoteArticlesDataSource,
) : ArticlesRepository {
    override suspend fun getAll() = remoteDataSource.getAll().apply {
        if (this is HttpResponseState.Success) {
            localDataSource.clear()
            localDataSource.insertAll(this.value)
            return@apply
        }

        with(localDataSource.getAll()) {
            if (isEmpty()) return@apply
            return HttpResponseState.Success(this)
        }
    }

    override suspend fun getAlarmCreationData(fileName: String) =
        remoteDataSource.getAlarmCreationData(fileName)
}
