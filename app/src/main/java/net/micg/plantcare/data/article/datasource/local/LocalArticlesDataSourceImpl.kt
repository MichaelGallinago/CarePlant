package net.micg.plantcare.data.article.datasource.local

import net.micg.plantcare.data.article.db.ArticleDao
import net.micg.plantcare.data.article.models.Article
import javax.inject.Inject

class LocalArticlesDataSourceImpl @Inject constructor(
    private val dao: ArticleDao,
) : LocalArticlesDataSource {
    override suspend fun insertAll(articles: List<Article>) = dao.insertAll(articles)
    override suspend fun getAll() = dao.getAll()
    override suspend fun clear() = dao.clear()
}
