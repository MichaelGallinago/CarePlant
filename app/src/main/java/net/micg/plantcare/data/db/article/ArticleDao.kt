package net.micg.plantcare.data.db.article

import androidx.room.*
import net.micg.plantcare.data.models.article.Article

@Dao
interface ArticleDao {
    @Upsert
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM articles ORDER BY title")
    suspend fun getAll(): List<Article>

    @Query("DELETE FROM articles")
    suspend fun clear()
}
