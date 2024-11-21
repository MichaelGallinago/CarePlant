package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.article.Article
import net.micg.plantcare.data.models.article.ArticleDao
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val articleDao: ArticleDao) :
    ArticlesRepository {
    override suspend fun insertAll(articles: List<Article>) = articleDao.insertAll(articles)
    override suspend fun getAll(): List<Article> = articleDao.getAll()
    override suspend fun clear() = articleDao.clear()
}
