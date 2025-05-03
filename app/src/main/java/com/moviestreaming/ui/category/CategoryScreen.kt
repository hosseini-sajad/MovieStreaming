package com.moviestreaming.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.data.model.MovieCategory
import com.moviestreaming.ui.home.LoadingAnimation
import com.moviestreaming.ui.home.getDummyMovies
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun CategoryScreenRoute(
    onBackClick: () -> Unit,
    category: MovieCategory,
    onMovieClick: (Int) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by when (category) {
        MovieCategory.TOP_RATED -> viewModel.topRatedUiState.collectAsState()
        MovieCategory.POPULAR -> viewModel.popularUiState.collectAsState()
    }

    val title = when (category) {
        MovieCategory.TOP_RATED -> stringResource(R.string.top_imdb)
        MovieCategory.POPULAR -> stringResource(R.string.popular_movies)
    }

    CategoryScreen(
        onBackClick = onBackClick,
        categoryName = title,
        onMovieClick = onMovieClick,
        uiState = uiState
    )
}

@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    categoryName: String,
    uiState: CategoryUiState,
    onMovieClick: (Int) -> Unit,
    gridColumns: Int = 3
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MovieStreamingTheme.colors.uiBackground)
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                HeaderCategory(
                    onBackClick = onBackClick,
                    categoryName = categoryName
                )
                when (uiState) {
                    is CategoryUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation()
                        }
                    }

                    is CategoryUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = uiState.message,
                                    color = MovieStreamingTheme.colors.selectIndicatorColor,
                                    modifier = Modifier.padding(16.dp)
                                )
                                IconButton(
                                    onClick = { }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_star),
                                        contentDescription = stringResource(R.string.retry),
                                        tint = MovieStreamingTheme.colors.selectIndicatorColor
                                    )
                                }
                            }
                        }
                    }

                    is CategoryUiState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(gridColumns),
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.movies) { movie ->
                                MovieCard(
                                    onClick = onMovieClick,
                                    movie = movie
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderCategory(
    onBackClick: () -> Unit,
    categoryName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MovieStreamingTheme.colors.startSliderColor,
                        MovieStreamingTheme.colors.toolbarCenterColor,
                        MovieStreamingTheme.colors.toolbarEndColor
                    )
                )
            )
    ) {
        Text(
            text = categoryName,
            color = MovieStreamingTheme.colors.selectIndicatorColor,
            fontFamily = FontFamily(Font(R.font.helvetica_bold)),
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = MovieStreamingTheme.colors.selectIndicatorColor
            )
        }
    }
}

@Preview(name = "Success State", showBackground = true)
@Composable
fun CategoryScreenSuccessTwoColumnsPreview() {
    MovieStreamingTheme {
        CategoryScreen(
            onBackClick = {},
            categoryName = "Popular",
            onMovieClick = {},
            uiState = CategoryUiState.Success(
                getDummyMovies(100)
            )
        )
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
fun CategoryScreenLoadingPreview() {
    MovieStreamingTheme {
        CategoryScreen(
            onBackClick = {},
            categoryName = "Top Rated",
            onMovieClick = {},
            uiState = CategoryUiState.Loading
        )
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
fun CategoryScreenErrorPreview() {
    MovieStreamingTheme {
        CategoryScreen(
            onBackClick = {},
            categoryName = "Popular",
            onMovieClick = {},
            uiState = CategoryUiState.Error("Failed to load movies. Please try again.")
        )
    }
}

@Preview(name = "Success State - Empty List", showBackground = true)
@Composable
fun CategoryScreenSuccessEmptyPreview() {
    MovieStreamingTheme {
        CategoryScreen(
            onBackClick = {},
            categoryName = "Popular",
            onMovieClick = {},
            uiState = CategoryUiState.Success(emptyList())
        )
    }
}