package com.example.newsapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM bookmarked_articles ORDER BY bookmarkedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT COUNT(*) FROM bookmarked_articles WHERE url = :url")
    fun isArticleBookmarked(url: String): Flow<Int>

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("DELETE FROM bookmarked_articles WHERE url = :url")
    suspend fun deleteArticleByUrl(url: String)

    @Query("DELETE FROM bookmarked_articles")
    suspend fun deleteAllArticles()
}