package net.micg.plantcare.data.models.article

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
