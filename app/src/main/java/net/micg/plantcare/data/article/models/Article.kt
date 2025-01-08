package net.micg.plantcare.data.article.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    val title: String,
    @PrimaryKey var name: String,
    val icon: String
)
