package net.micg.plantcare.data.models.article

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    val title: String,
    @PrimaryKey var url: String,
    val icon: String
)
