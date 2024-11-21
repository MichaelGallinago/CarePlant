package net.micg.plantcare.data.article

import net.micg.plantcare.data.models.article.Article

interface ArticlesRepository {
    suspend fun insertAll(articles: List<Article>)
    suspend fun getAll(): List<Article>
    suspend fun clear()
}
