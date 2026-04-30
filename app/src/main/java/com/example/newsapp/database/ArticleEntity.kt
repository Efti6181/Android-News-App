package com.example.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class ArticleEntity(
    @PrimaryKey
    val url: String,
    val title: String?,
    val description: String?,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val author: String?,
    val sourceName: String?,
    val bookmarkedAt: Long = System.currentTimeMillis()
)