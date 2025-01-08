package net.micg.plantcare.data.article.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.micg.plantcare.data.article.models.Article

@Database(entities = [Article::class], version = 4, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
