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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.ui.home.getDummyMovies
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun CategoryScreenRoute(
    onBackClick: () -> Unit
) {
    CategoryScreen(
        onBackClick = onBackClick,
        categoryName = "",
        movies = getDummyMovies()
    )
}

@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    categoryName: String,
    movies: List<TopRateMovieEntity>
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MovieStreamingTheme.colors.uiBackground)
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                HeaderCategory(
                    onBackClick = onBackClick,
                    categoryName = categoryName
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        MovieCard(
                            onClick = {},
                            movie = movie
                        )
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
            .background(MovieStreamingTheme.colors.uiBackground)
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

@Preview
@Composable
fun CategoryScreenPreview() {
    MovieStreamingTheme {
        CategoryScreen(
            onBackClick = {},
            categoryName = "More",
            movies = getDummyMovies()
        )
    }
}