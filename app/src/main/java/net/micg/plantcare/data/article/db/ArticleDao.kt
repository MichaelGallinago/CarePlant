package net.micg.plantcare.data.article.db

import androidx.room.*
import net.micg.plantcare.data.article.models.Article

@Dao
interface ArticleDao {
    @Upsert
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM articles")
    suspend fun getAll(): List<Article>

    @Query("DELETE FROM articles")
    suspend fun clear()
}
