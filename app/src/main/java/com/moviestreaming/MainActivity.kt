package com.moviestreaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moviestreaming.data.model.MovieCategory
import com.moviestreaming.ui.category.CategoryScreenRoute
import com.moviestreaming.ui.category.CategoryViewModel
import com.moviestreaming.ui.detail.DetailScreenRoute
import com.moviestreaming.ui.detail.DetailViewModel
import com.moviestreaming.ui.home.HomeScreenRoute
import com.moviestreaming.ui.navigation.NavigationItem
import com.moviestreaming.ui.search.SearchScreenRoute
import com.moviestreaming.ui.theme.MovieStreamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieStreamingApp()
        }
    }
}

@Composable
fun MovieStreamingApp() {
    MovieStreamingTheme {
        val navController = rememberNavController()
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Search
        )
        Scaffold(
            bottomBar = { BottomNavigationBar(navController, items) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationGraph(navController = navController)
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreenRoute(
                onClick = { movieId ->
                    navController.navigate("movie/${movieId}")
                },
                onMoreClick = { category ->
                    navController.navigate("category/${category}")
                }
            )
        }
        composable(NavigationItem.Search.route) {
            SearchScreenRoute()
        }
        composable(
            route = NavigationItem.MovieDetails.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { navBackStackEntry ->
            val detailViewModel = hiltViewModel<DetailViewModel>()
            DetailScreenRoute(
                viewModel = detailViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                similarOnClick = { similarMovieId ->
                    navController.navigate("movie/${similarMovieId}")
                }
            )
        }
        composable(
            route = NavigationItem.Category.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val category = navBackStackEntry.arguments?.getString("category") ?: "POPULAR"
            val categoryType = try {
                MovieCategory.valueOf(category)
            } catch (e: Exception) {
                MovieCategory.POPULAR
            }

            CategoryScreenRoute(
                viewModel = hiltViewModel<CategoryViewModel>(),
                onBackClick = {
                    navController.popBackStack()
                },
                category = categoryType,
                onMovieClick = { movieId ->
                    navController.navigate("movie/${movieId}")
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<NavigationItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    item.icon?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = item.label
                        )
                    }
                },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

