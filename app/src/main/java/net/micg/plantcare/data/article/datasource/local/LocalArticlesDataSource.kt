package net.micg.plantcare.data.article.datasource.local

import net.micg.plantcare.data.article.models.Article

interface LocalArticlesDataSource {
    suspend fun insertAll(articles: List<Article>)
    suspend fun getAll(): List<Article>
    suspend fun clear()
}
