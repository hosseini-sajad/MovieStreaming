package com.moviestreaming.ui.navigation

import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moviestreaming.R
import com.moviestreaming.ui.home.HomeScreen
import com.moviestreaming.ui.search.SearchScreen

sealed class NavigationItem(
    val route: String,
    val icon: Int? = null,
    val label: String
) {
    object Home : NavigationItem(
        route = HOME_SCREEN_ROUTE,
        icon = R.drawable.ic_home_black_24dp,
        label = "Home"
    )
    object Search : NavigationItem(
        route = SEARCH_SCREEN_ROUTE,
        icon = R.drawable.ic_search,
        label = "Search"
    )
    object MovieDetails : NavigationItem(
        route = MOVIE_DETAIL_SCREEN_ROUTE,
        label = "Movie Details"
    )
    object Category : NavigationItem(
        route = CATEGORY_SCREEN_ROUTE,
        label = "Category"
    )
}

private const val HOME_SCREEN_ROUTE = "home"
private const val SEARCH_SCREEN_ROUTE = "search"
private const val MOVIE_DETAIL_SCREEN_ROUTE = "movie/{movieId}"
private const val CATEGORY_SCREEN_ROUTE = "category/{category}"