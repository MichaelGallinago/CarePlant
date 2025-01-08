package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.article.db.ArticleDatabase

@Module
class ArticleDatabaseModule {
    @Provides
    @AppComponentScope
    fun provideDatabase(context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_database"
        ).build()
    }

    @Provides
    @AppComponentScope
    fun provideDao(database: ArticleDatabase) = database.articleDao()
}
