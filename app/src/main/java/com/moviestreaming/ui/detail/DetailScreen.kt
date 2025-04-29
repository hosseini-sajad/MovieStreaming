package com.moviestreaming.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.moviestreaming.R
import com.moviestreaming.core.component.CastCard
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.data.model.CreditsEntity
import com.moviestreaming.data.model.GenreEntity
import com.moviestreaming.data.model.MovieDetailEntity
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.ui.home.LoadingAnimation
import com.moviestreaming.ui.theme.MovieStreamingTheme
import com.moviestreaming.utils.getImageUrl

@Composable
fun DetailScreenRoute(
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.detailUiState.collectAsState()

    DetailScreen(
        uiState = uiState,
        onBackClick = {},
        onBookmarkClick = {},
        onTrialClick = {}
    )
}

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onTrialClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val toolbarHeight = 250.dp
    val minToolbarHeight = 60.dp
    val toolbarOffset by remember {
        derivedStateOf {
            (toolbarHeight - (scrollState.value / 2).dp).coerceAtLeast(minToolbarHeight)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> LoadingAnimation(
                modifier = Modifier.align(Alignment.Center)
            )

            uiState.errorMessage != null -> ErrorMessage(
                message = uiState.errorMessage,
                modifier = Modifier.align(Alignment.Center)
            )

            else -> DetailContent(
                uiState = uiState,
                scrollState = scrollState,
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffset,
                minToolbarHeight = minToolbarHeight,
                onBackClick = onBackClick,
                onBookmarkClick = onBookmarkClick,
                onTrialClick = onTrialClick
            )
        }
    }
}

@Composable
private fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        color = MovieStreamingTheme.colors.selectIndicatorColor,
        modifier = modifier
    )
}

@Composable
private fun DetailContent(
    uiState: DetailUiState,
    scrollState: ScrollState,
    toolbarHeight: Dp,
    toolbarOffset: Dp,
    minToolbarHeight: Dp,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onTrialClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(toolbarHeight))
            ContentSection(
                movieRate = uiState.movieDetail?.rate ?: 0.0,
                releasedMovieDate = uiState.movieDetail?.releasedDate ?: "",
                directors = uiState.crewMovie.toString(),
                movieBudget = uiState.movieDetail?.budget ?: 1,
                movieDescription = uiState.movieDetail?.description ?: "",
                casts = uiState.castMovie ?: emptyList(),
                similarMovies = uiState.similarMovies ?: emptyList()
            )
        }
        
        Box(modifier = Modifier.fillMaxSize()) {
            HeaderDetail(
                toolbarOffset = toolbarOffset,
                minToolbarHeight = minToolbarHeight,
                uiState = uiState,
                onBackClick = onBackClick,
                onBookmarkClick = onBookmarkClick
            )
            
            if (scrollState.value == 0) {
                val fabSize = 56.dp
                PlayButton(
                    toolbarHeight = toolbarHeight,
                    onClick = onTrialClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 10.dp)
                        .offset(y = toolbarHeight - (fabSize / 2))
                )
            }
        }
    }
}

@Composable
private fun PlayButton(
    toolbarHeight: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier,
        containerColor = MovieStreamingTheme.colors.selectIndicatorColor
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_play),
            contentDescription = null,
            tint = MovieStreamingTheme.colors.uiBackground
        )
    }
}

@Composable
private fun HeaderDetail(
    toolbarOffset: Dp,
    minToolbarHeight: Dp,
    uiState: DetailUiState,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(toolbarOffset)
            .fillMaxWidth()
            .background(
                if (toolbarOffset <= minToolbarHeight)
                    MovieStreamingTheme.colors.uiBackground
                else Color.Transparent
            )
    ) {
        if (toolbarOffset > minToolbarHeight) {
            MovieBackdrop(
                imageUrl = uiState.movieDetail?.image,
                title = uiState.movieDetail?.title
            )
        }

        HeaderActions(
            onBackClick = onBackClick,
            onBookmarkClick = onBookmarkClick
        )

        if (toolbarOffset > minToolbarHeight) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp, bottom = 20.dp)
            ) {
                MovieInfo(
                    title = uiState.movieDetail?.title ?: "",
                    genres = uiState.movieDetail?.genres?.map { it.name } ?: emptyList()
                )
            }
        }
    }
}

@Composable
private fun MovieBackdrop(
    imageUrl: String?,
    title: String?
) {
    val isInPreview = LocalInspectionMode.current

    if (isInPreview) {
        Image(
            painter = painterResource(R.drawable.tenet),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    } else {
        AsyncImage(
            model = imageUrl?.let { getImageUrl(it) },
            contentDescription = title,
            error = painterResource(R.drawable.ic_image_error),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun HeaderActions(
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = MovieStreamingTheme.colors.selectIndicatorColor
            )
        }
        IconButton(onClick = onBookmarkClick) {
            Icon(
                painter = painterResource(R.drawable.ic_bookmark_inactive),
                contentDescription = null,
                tint = MovieStreamingTheme.colors.selectIndicatorColor
            )
        }
    }
}

@Composable
private fun MovieInfo(
    title: String,
    genres: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.nexa_bold)),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 5.dp),
            color = MovieStreamingTheme.colors.titleColor
        )
        GenreList(genres = genres)
    }
}

@Composable
private fun GenreList(genres: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        items(genres) { genre ->
            GenreChip(genre = genre)
        }
    }
}

@Composable
private fun GenreChip(genre: String) {
    Text(
        text = genre,
        color = MovieStreamingTheme.colors.titleColor,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.helvetica)),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MovieStreamingTheme.colors.genreBackgroundColor)
            .padding(vertical = 3.dp, horizontal = 5.dp)
    )
}

@Composable
fun ContentSection(
    movieRate: Double,
    releasedMovieDate: String,
    directors: String,
    movieBudget: Int,
    movieDescription: String,
    casts: List<CreditsEntity.CastEntity>,
    similarMovies: List<TopRateMovieEntity>,
) {
    Column(modifier = Modifier.padding(10.dp)) {
        RateSection(movieRate, releasedMovieDate)
        MovieDetails(
            directors = directors,
            budget = movieBudget
        )
        DescriptionSection(description = movieDescription)
        CastSection(casts = casts)
        SimilarMoviesSection(similarMovies = similarMovies)
    }
}

@Composable
private fun MovieDetails(
    directors: String,
    budget: Int
) {
    Column(
        modifier = Modifier.padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.director),
                color = MovieStreamingTheme.colors.titleColor,
                fontFamily = FontFamily(Font(R.font.helvetica)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = directors,
                color = MovieStreamingTheme.colors.titleColor,
                fontFamily = FontFamily(Font(R.font.helvetica)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        Row {
            Text(
                text = stringResource(R.string.budget),
                color = MovieStreamingTheme.colors.titleColor,
                fontFamily = FontFamily(Font(R.font.helvetica)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = budget.toString(),
                color = MovieStreamingTheme.colors.titleColor,
                fontFamily = FontFamily(Font(R.font.helvetica)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
private fun DescriptionSection(description: String) {
    Column {
        SectionTitle(text = stringResource(R.string.description))
        Text(
            text = description,
            color = MovieStreamingTheme.colors.titleColor,
            fontFamily = FontFamily(Font(R.font.helvetica)),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 19.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .defaultMinSize(minHeight = 120.dp)
        )
    }
}

@Composable
private fun CastSection(casts: List<CreditsEntity.CastEntity>) {
    Column {
        SectionTitle(text = stringResource(R.string.cast))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(top = 12.dp)
                .defaultMinSize(minHeight = 150.dp)
        ) {
            items(casts) { cast ->
                CastCard(
                    castImage = cast.profileImage?.let { getImageUrl(it) },
                    castName = cast.realName
                )
            }
        }
    }
}

@Composable
private fun SimilarMoviesSection(similarMovies: List<TopRateMovieEntity>) {
    Column {
        SectionTitle(text = stringResource(R.string.similar_movies))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 12.dp)
                .defaultMinSize(minHeight = 150.dp)
        ) {
            items(similarMovies) { movie ->
                MovieCard(
                    onClick = {},
                    movie = movie
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.nexa_bold)),
        fontSize = 15.sp,
        modifier = Modifier
            .padding(top = 20.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(MovieStreamingTheme.colors.selectIndicatorColor)
            .padding(5.dp)
    )
}

@Composable
private fun RateSection(movieRate: Double, releasedMovieDate: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_imdb),
            contentDescription = null
        )
        Text(
            text = movieRate.toString(),
            color = MovieStreamingTheme.colors.titleColor,
            modifier = Modifier.padding(start = 10.dp, top = 2.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.helvetica))
        )
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .width(2.dp)
                .height(18.dp)
                .background(
                    color = MovieStreamingTheme.colors.selectIndicatorColor
                )
        )
        Text(
            text = releasedMovieDate,
            fontFamily = FontFamily(Font(R.font.helvetica)),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MovieStreamingTheme.colors.titleColor,
            modifier = Modifier
                .padding(start = 10.dp, top = 2.dp)
        )
    }
}

@Preview(name = "Full Content", showBackground = true)
@Composable
private fun DetailScreenFullContentPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(
                isLoading = false,
                movieDetail = MovieDetailEntity(
                    id = 1,
                    title = "The Dark Knight",
                    image = "https://example.com/image.jpg",
                    mediaType = "movie",
                    rate = 9.0,
                    releasedDate = "2008-07-18",
                    budget = 185000000,
                    description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                    genres = listOf(
                        GenreEntity(1, "Action"),
                        GenreEntity(2, "Crime"),
                        GenreEntity(3, "Drama")
                    )
                ),
                castMovie = listOf(
                    CreditsEntity.CastEntity(
                        castId = 1,
                        realName = "Christian Bale",
                        character = "Bruce Wayne / Batman",
                        knownForDepartment = "Acting",
                        profileImage = "https://example.com/bale.jpg",
                        gender = 2,
                        popularity = 84.5,
                        creditId = "52fe4284c3a36847f8024f95"
                    ),
                    CreditsEntity.CastEntity(
                        castId = 2,
                        realName = "Heath Ledger",
                        character = "Joker",
                        knownForDepartment = "Acting",
                        profileImage = "https://example.com/ledger.jpg",
                        gender = 2,
                        popularity = 82.3,
                        creditId = "52fe4284c3a36847f8024f96"
                    )
                ),
                crewMovie = "Christopher Nolan",
                similarMovies = listOf(
                    TopRateMovieEntity(
                        id = 2,
                        title = "Inception",
                        image = "https://example.com/inception.jpg",
                        mediaType = "movie",
                        rate = 8.8,
                        genre = 1
                    ),
                    TopRateMovieEntity(
                        id = 3,
                        title = "Interstellar",
                        image = "https://example.com/interstellar.jpg",
                        mediaType = "movie",
                        rate = 8.9,
                        genre = 2
                    )
                )
            ),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun DetailScreenEmptyPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(
                isLoading = false,
                movieDetail = null,
                castMovie = emptyList(),
                crewMovie = null,
                similarMovies = emptyList()
            ),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}

@Preview(name = "Partial Content", showBackground = true)
@Composable
private fun DetailScreenPartialContentPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(
                isLoading = false,
                movieDetail = MovieDetailEntity(
                    id = 1,
                    title = "The Dark Knight",
                    image = "https://example.com/image.jpg",
                    mediaType = "movie",
                    rate = 9.0,
                    releasedDate = "2008-07-18",
                    budget = 185000000,
                    description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                    genres = listOf(
                        GenreEntity(1, "Action"),
                        GenreEntity(2, "Crime"),
                        GenreEntity(3, "Drama")
                    )
                ),
                castMovie = null,
                crewMovie = null,
                similarMovies = null
            ),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}

@Preview(name = "Default", showBackground = true)
@Composable
private fun DetailScreenDefaultPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun DetailScreenLoadingPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(isLoading = true),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}

@Preview(name = "Error", showBackground = true)
@Composable
private fun DetailScreenErrorPreview() {
    MovieStreamingTheme {
        DetailScreen(
            uiState = DetailUiState(
                isLoading = false,
                errorMessage = "Failed to load movie details"
            ),
            onBackClick = {},
            onBookmarkClick = {},
            onTrialClick = {}
        )
    }
}