package net.micg.plantcare.data.article

import net.micg.plantcare.data.db.article.ArticleDao
import net.micg.plantcare.data.models.article.Article
import javax.inject.Inject

class LocalArticlesDataSourceImpl @Inject constructor(
    private val dao: ArticleDao,
) : LocalArticlesDataSource {
    override suspend fun insertAll(articles: List<Article>) = dao.insertAll(articles)
    override suspend fun getAll() = dao.getAll()
    override suspend fun clear() = dao.clear()
}
