package net.micg.plantcare.data.models.article

import androidx.room.*

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM articles ORDER BY title")
    suspend fun getAll(): List<Article>

    @Query("DELETE FROM articles")
    suspend fun clear()
}
