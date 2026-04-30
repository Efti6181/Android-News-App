package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.database.ArticleEntity
import com.example.newsapp.model.Article
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewsUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)

data class SearchUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val query: String = "",
    val hasSearched: Boolean = false
)

data class BookmarkUiState(
    val isLoading: Boolean = false,
    val bookmarks: List<ArticleEntity> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow(NewsUiState())
    val newsState: StateFlow<NewsUiState> = _newsState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private val _bookmarkState = MutableStateFlow(BookmarkUiState())
    val bookmarkState: StateFlow<BookmarkUiState> = _bookmarkState.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    init {
        fetchTopHeadlines()
        loadBookmarks()
    }

    fun fetchTopHeadlines(country: String = "us") {
        viewModelScope.launch {
            _newsState.value = NewsUiState(isLoading = true)
            when (val result = repository.getTopHeadlines(country)) {
                is Resource.Success -> {
                    val articles = result.data?.articles
                        ?.filter { it.title != null && it.title != "[Removed]" }
                        ?: emptyList()
                    _newsState.value = NewsUiState(articles = articles)
                }
                is Resource.Error -> {
                    _newsState.value = NewsUiState(error = result.message)
                }
                is Resource.Loading -> {
                    _newsState.value = NewsUiState(isLoading = true)
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchState.value = _searchState.value.copy(query = query)
    }

    fun searchNews() {
        val query = _searchState.value.query.trim()
        if (query.isEmpty()) {
            _snackbarMessage.value = "Please enter a search term"
            return
        }
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(
                isLoading = true, hasSearched = true, error = null
            )
            when (val result = repository.searchNews(query)) {
                is Resource.Success -> {
                    val articles = result.data?.articles
                        ?.filter { it.title != null && it.title != "[Removed]" }
                        ?: emptyList()
                    _searchState.value = _searchState.value.copy(
                        isLoading = false,
                        articles = articles,
                        error = if (articles.isEmpty()) "No results for \"$query\"" else null
                    )
                }
                is Resource.Error -> {
                    _searchState.value = _searchState.value.copy(
                        isLoading = false, error = result.message
                    )
                }
                is Resource.Loading -> {
                    _searchState.value = _searchState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun clearSearch() {
        _searchState.value = SearchUiState()
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            repository.getAllBookmarkedArticles().collect { bookmarks ->
                _bookmarkState.value = BookmarkUiState(bookmarks = bookmarks)
            }
        }
    }

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch {
            try {
                repository.bookmarkArticle(article)
                _snackbarMessage.value = "Article bookmarked!"
            } catch (e: Exception) {
                _snackbarMessage.value = "Failed to bookmark"
            }
        }
    }

    fun removeBookmark(article: ArticleEntity) {
        viewModelScope.launch {
            try {
                repository.deleteBookmark(article)
                _snackbarMessage.value = "Bookmark removed"
            } catch (e: Exception) {
                _snackbarMessage.value = "Failed to remove"
            }
        }
    }

    fun removeBookmarkByUrl(url: String) {
        viewModelScope.launch {
            try {
                repository.deleteBookmarkByUrl(url)
                _snackbarMessage.value = "Bookmark removed"
            } catch (e: Exception) {
                _snackbarMessage.value = "Failed to remove"
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            try {
                repository.deleteAllBookmarks()
                _snackbarMessage.value = "All bookmarks cleared"
            } catch (e: Exception) {
                _snackbarMessage.value = "Failed to clear"
            }
        }
    }

    fun isBookmarked(url: String): StateFlow<Boolean> {
        return repository.isArticleBookmarked(url)
            .map { it > 0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    }

    fun setSelectedArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun clearSelectedArticle() {
        _selectedArticle.value = null
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }
}