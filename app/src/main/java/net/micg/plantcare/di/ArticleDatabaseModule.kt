package net.micg.plantcare.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import net.micg.plantcare.data.article.ArticlesApi
import net.micg.plantcare.data.models.article.ArticleDao
import net.micg.plantcare.data.models.article.ArticleDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ArticleDatabaseModule {
    @Provides
    fun provideDatabase(context: Context): ArticleDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_database"
        )
            .fallbackToDestructiveMigration() //TODO: REMOVE THIS
            .build()
    }

    @Provides
    fun provideDao(database: ArticleDatabase) = database.articleDao()
}

