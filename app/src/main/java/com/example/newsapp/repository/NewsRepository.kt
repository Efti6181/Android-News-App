package com.example.newsapp.repository

import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleEntity
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.Source
import com.example.newsapp.network.NewsApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) {

    suspend fun getTopHeadlines(country: String = "us"): Resource<NewsResponse> {
        return try {
            handleResponse(newsApi.getTopHeadlines(country = country))
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun searchNews(query: String): Resource<NewsResponse> {
        return try {
            handleResponse(newsApi.searchNews(query = query))
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("Empty response")
        } else {
            Resource.Error("Error ${response.code()}: ${response.message()}")
        }
    }

    suspend fun bookmarkArticle(article: Article) {
        articleDao.insertArticle(article.toEntity())
    }

    fun getAllBookmarkedArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getAllArticles()
    }

    fun isArticleBookmarked(url: String): Flow<Int> {
        return articleDao.isArticleBookmarked(url)
    }

    suspend fun deleteBookmark(article: ArticleEntity) {
        articleDao.deleteArticle(article)
    }

    suspend fun deleteBookmarkByUrl(url: String) {
        articleDao.deleteArticleByUrl(url)
    }

    suspend fun deleteAllBookmarks() {
        articleDao.deleteAllArticles()
    }
}

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        url = this.url ?: "",
        title = this.title,
        description = this.description,
        content = this.content,
        imageUrl = this.urlToImage,
        publishedAt = this.publishedAt,
        author = this.author,
        sourceName = this.source?.name
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        source = Source(id = null, name = this.sourceName),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.imageUrl,
        publishedAt = this.publishedAt,
        content = this.content
    )
}