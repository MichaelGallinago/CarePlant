package net.micg.plantcare.data.db.article

import androidx.room.Database
import androidx.room.RoomDatabase
import net.micg.plantcare.data.models.article.Article

@Database(entities = [Article::class], version = 2, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
