package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.ui.screen.BookmarkScreen
import com.example.newsapp.ui.screen.DetailsScreen
import com.example.newsapp.ui.screen.HomeScreen
import com.example.newsapp.ui.screen.SearchScreen
import com.example.newsapp.viewmodel.NewsViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Routes {
    const val HOME = "home"
    const val DETAILS = "details/{articleUrl}"
    const val SEARCH = "search"
    const val BOOKMARK = "bookmark"

    fun detailsRoute(url: String): String {
        val encodedUrl = URLEncoder.encode(
            url,
            StandardCharsets.UTF_8.toString()
        )
        return "details/$encodedUrl"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: NewsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        // Home Screen
        composable(route = Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    viewModel.setSelectedArticle(article)
                    val url = article.url
                    if (url != null) {
                        navController.navigate(Routes.detailsRoute(url))
                    }
                },
                onNavigateToSearch = {
                    navController.navigate(Routes.SEARCH)
                },
                onNavigateToBookmark = {
                    navController.navigate(Routes.BOOKMARK)
                }
            )
        }

        // Details Screen
        composable(
            route = Routes.DETAILS,
            arguments = listOf(
                navArgument("articleUrl") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val encodedUrl =
                backStackEntry.arguments?.getString("articleUrl") ?: ""
            val articleUrl = URLDecoder.decode(
                encodedUrl,
                StandardCharsets.UTF_8.toString()
            )
            DetailsScreen(
                articleUrl = articleUrl,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Search Screen
        composable(route = Routes.SEARCH) {
            SearchScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    viewModel.setSelectedArticle(article)
                    val url = article.url
                    if (url != null) {
                        navController.navigate(Routes.detailsRoute(url))
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Bookmark Screen
        composable(route = Routes.BOOKMARK) {
            BookmarkScreen(
                viewModel = viewModel,
                onArticleClick = { article ->
                    viewModel.setSelectedArticle(article)
                    val url = article.url
                    if (url != null) {
                        navController.navigate(Routes.detailsRoute(url))
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}