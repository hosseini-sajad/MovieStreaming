package com.moviestreaming.ui.home

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.core.component.MovieSliderItem
import com.moviestreaming.data.model.MovieCategory
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.ui.theme.MovieStreamingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreenRoute(
    onClick: (movieId: Int) -> Unit,
    onMoreClick: (category: MovieCategory) -> Unit
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val uiState by homeViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    HomeScreen(
        uiState = uiState,
        onClick = onClick,
        onRetry = { homeViewModel.retry() },
        snackBarHostState = snackBarHostState,
        onMoreClick = onMoreClick
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onClick: (movieId: Int) -> Unit,
    onRetry: () -> Unit,
    snackBarHostState: SnackbarHostState,
    onMoreClick: (category: MovieCategory) -> Unit
) {
    val scrollState = rememberScrollState()
    val topIMDbPagingData = uiState.topIMDbMovies.collectAsLazyPagingItems()
    val topIMDbMovies = (0 until minOf(5, topIMDbPagingData.itemCount)).mapNotNull{ index ->
        topIMDbPagingData[index]
    }
    val popularMoviesPagingData = uiState.popularMovies.collectAsLazyPagingItems()
    val popularMovies = (0 until minOf(5, popularMoviesPagingData.itemCount)).mapNotNull { index ->
        popularMoviesPagingData[index]
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    LoadingAnimation()
                }

                uiState.errorMessage != null && uiState.trendingMovies.isEmpty() &&
                        topIMDbMovies.isEmpty() && popularMovies.isEmpty() -> {
                    ErrorBanner(
                        message = uiState.errorMessage,
                        onRetry = onRetry
                    )
                }

                else -> {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        if (uiState.trendingMovies.isNotEmpty()) {
                            MovieSlider(
                                movies = uiState.trendingMovies,
                                onClick = onClick
                            )
                        }

                        if (topIMDbMovies.isNotEmpty()) {
                            TopicSection(
                                title = R.string.top_imdb,
                                movies = topIMDbMovies,
                                onMoreClick = onMoreClick,
                                onClick = onClick
                            )
                        }

                        if (popularMovies.isNotEmpty()) {
                            TopicSection(
                                title = R.string.popular_movies,
                                movies = popularMovies,
                                onMoreClick = onMoreClick,
                                onClick = onClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorBanner(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(8.dp))
        TextButton(onClick = onRetry) {
            Text(
                text = stringResource(R.string.retry),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MovieSlider(
    movies: List<TrendingEntity>,
    onClick: (movieId: Int) -> Unit
) {
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
            onClick = onClick
        )
    }
}

@Composable
fun TopicSection(
    @StringRes title: Int,
    movies: List<TopRateMovieEntity>,
    onMoreClick: (category: MovieCategory) -> Unit,
    onClick: (movieId: Int) -> Unit
) {
    Column {
        Row {
            TitleSection(
                title = title
            )
            Spacer(modifier = Modifier.weight(1f))
            MoreText {
                val category = when (title) {
                    R.string.top_imdb -> MovieCategory.TOP_RATED
                    R.string.popular_movies -> MovieCategory.POPULAR
                    else -> MovieCategory.TOP_RATED
                }
                onMoreClick(category)
            }
        }
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(movies.take(5)) { movie ->
                MovieCard(
                    onClick = onClick,
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
fun LoadingAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading3))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = modifier
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

@Preview(name = "Content", showBackground = true)
@Composable
fun HomeScreenContentPreview() {
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
            )
        )
        HomeScreen(
            uiState = HomeUiState(
                trendingMovies = trendingSamples,
                topIMDbMovies = flowOf(
                    PagingData.from(getDummyMovies())
                ),
                popularMovies = flowOf(
                    PagingData.from(getDummyMovies())
                ),
                isLoading = false
            ),
            onClick = {},
            onRetry = {},
            snackBarHostState = remember { SnackbarHostState() },
            onMoreClick = {}
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