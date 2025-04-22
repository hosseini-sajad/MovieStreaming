package com.moviestreaming.ui.home

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.core.component.MovieSliderItem
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.ui.theme.MovieStreamingTheme
import kotlinx.coroutines.delay

@Composable
fun HomeScreenRoute() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(
    uiState: HomeUiState
) {

    val scrollState = rememberScrollState()

    Scaffold() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    LoadingAnimation()
                }

                uiState.errorMessage != null && uiState.trendingMovies.isEmpty() &&
                        uiState.topIMDbMovies.isEmpty() && uiState.popularMovies.isEmpty() -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MovieStreamingTheme.colors.selectIndicatorColor
                    )
                }

                else -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        if (uiState.errorMessage != null) {
                            Text(
                                text = uiState.errorMessage,
                                color = MovieStreamingTheme.colors.selectIndicatorColor
                            )
//                            ErrorBanner(message = uiState.errorMessage, onRetry = onRetry)
                        }

                        if (uiState.trendingMovies.isNotEmpty()) {
                            MovieSlider(uiState.trendingMovies)
                        }

                        if (uiState.topIMDbMovies.isNotEmpty()) {
                            TopicSection(
                                title = R.string.top_imdb,
                                movies = uiState.topIMDbMovies,
                                onclick = {}
                            )
                        }

                        if (uiState.popularMovies.isNotEmpty()) {
                            TopicSection(
                                title = R.string.popular_movies,
                                movies = uiState.popularMovies,
                                onclick = {}
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MovieSlider(movies: List<TrendingEntity>) {
    val sliderItems = movies.take(7)
    val pageCount = sliderItems.size
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    var lastUserInteraction by remember { mutableLongStateOf(0L) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }.collect { isScrolling ->
            if (isScrolling) {
                lastUserInteraction = System.currentTimeMillis()
            }
        }
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            val timeSinceInteraction = System.currentTimeMillis() - lastUserInteraction
            if (timeSinceInteraction > 3000) {
                val nextPage = (pagerState.currentPage + 1) % pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1
    ) { page ->
        MovieSliderItem(
            movie = sliderItems[page],
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(500)),
            pagerState = pagerState,
        )
    }
}

@Composable
fun TopicSection(
    @StringRes title: Int,
    movies: List<TopRateMovieEntity>,
    onclick: () -> Unit
) {
    Column {
        Row {
            TitleSection(
                title = title
            )
            Spacer(modifier = Modifier.weight(1f))
            MoreText {
                onclick()
            }
        }
        LazyRow(
            modifier = Modifier.padding(8.dp),
        ) {
            items(movies.take(5)) { movie ->
                MovieCard(
                    modifier = Modifier.padding(end = 5.dp),
                    movie = movie
                )
            }
        }
    }
}

@Composable
private fun TitleSection(
    @StringRes title: Int
) {
    Box(
        modifier = Modifier
            .padding(start = 12.dp, top = 10.dp)
            .background(
                color = MovieStreamingTheme.colors.selectIndicatorColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(horizontal = 5.dp, vertical = 0.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = title),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.nexa_bold)),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MoreText(onclick: () -> Unit) {
    TextButton(onClick = onclick) {
        Text(
            text = stringResource(R.string.more),
            modifier = Modifier
                .padding(end = 12.dp, top = 0.dp),
            color = MovieStreamingTheme.colors.selectIndicatorColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nexa_bold))
        )
    }
}

@Composable
fun LoadingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading3))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(top = 16.dp)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieStreamingTheme {
        val trendingSamples = listOf(
            TrendingEntity(
                id = 1,
                title = "Dune: Part Two",
                image = "https://image.tmdb.org/t/p/w500/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg",
                mediaType = "movie"
            ),
            TrendingEntity(
                id = 2,
                title = "Avatar: The Way of Water",
                image = "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
                mediaType = "movie"
            ),
            TrendingEntity(
                id = 3,
                title = "Stranger Things",
                image = "https://image.tmdb.org/t/p/w500/x2LSRK2Cm7MZhjluni1msVJ3wDF.jpg",
                mediaType = "tv"
            ),
            TrendingEntity(
                id = 4,
                title = "Breaking Bad",
                image = "https://image.tmdb.org/t/p/w500/ggFHVNu6YYI5L9pCfOacjizRGt.jpg",
                mediaType = "tv"
            ),
            TrendingEntity(
                id = 5,
                title = "The Batman",
                image = "https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg",
                mediaType = "movie"
            )
        )
        HomeScreen(
            uiState = HomeUiState(
                trendingMovies = trendingSamples,
                topIMDbMovies = getDummyMovies(5),
                popularMovies = getDummyMovies(5),
                isLoading = false
            )
        )
    }
}

@Preview
@Composable
fun HomeScreenErrorPreview() {
    MovieStreamingTheme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                errorMessage = "Failed to connect to server",
                trendingMovies = emptyList(),
                topIMDbMovies = emptyList(),
                popularMovies = emptyList()
            )
        )
    }
}

fun getDummyMovies(count: Int = 5): List<TopRateMovieEntity> {
    return List(count) { index ->
        TopRateMovieEntity(
            id = index,
            title = "Movie $index",
            image = null, // or a fake URL if needed
            mediaType = "movie",
            genre = 28,
            rate = (5..10).random().toDouble()
        )
    }
}