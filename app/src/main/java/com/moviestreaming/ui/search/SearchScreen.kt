package com.moviestreaming.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.data.model.TopRateMovieEntity
import com.moviestreaming.domain.usecase.SearchFilter
import com.moviestreaming.ui.home.LoadingAnimation
import com.moviestreaming.ui.theme.MovieStreamingTheme
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SearchScreen(
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onFilterSelected = viewModel::onFilterSelected,
        onMovieClick = {}
    )
}

@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onFilterSelected: (SearchFilter) -> Unit,
    onMovieClick: (Int) -> Unit,
    gridColumns: Int = 3
) {
    val pagingItems = if (uiState.query.isNotBlank()) {
        uiState.searchResults.collectAsLazyPagingItems()
    } else null

//    val movies = pagingItems?.itemSnapshotList?.items ?: emptyList()
    val isLoading = pagingItems?.loadState?.refresh is LoadState.Loading
    val error = (pagingItems?.loadState?.refresh as? LoadState.Error)?.error?.localizedMessage

    val appendLoading = pagingItems?.loadState?.append is LoadState.Loading
    val appendError = (pagingItems?.loadState?.append as? LoadState.Error)?.error?.localizedMessage



    SearchContent(
        uiState = uiState,
        onQueryChange = onQueryChange,
        onFilterSelected = onFilterSelected,
        movies = pagingItems,
        isLoading = isLoading,
        error = error,
        appendLoading = appendLoading,
        appendError = appendError,
        gridColumns = gridColumns,
        onMovieClick = onMovieClick
    )
}

//@Composable
//private fun SearchContent(
//    uiState: SearchUiState,
//    onQueryChange: (String) -> Unit,
//    onFilterSelected: (SearchFilter) -> Unit,
//    movies: LazyPagingItems<TopRateMovieEntity>?,
//    gridColumns: Int,
//    onMovieClick: (Int) -> Unit
//) {
//    Scaffold {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//        ) {
//            Column {
//                SearchHeader(
//                    query = uiState.query,
//                    onQueryChange = onQueryChange,
//                    onBackClick = {}
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                FilterChips(
//                    selectedFilter = uiState.selectedFilter.ordinal,
//                    onFilterSelected = onFilterSelected
//                )
//
//                uiState.error?.let { error ->
//                    Text(
//                        text = error,
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
//                }
//
//                if (movies == null) {
//                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                        Text(
//                            "Search movies by ${SearchFilter.fromString(uiState.selectedFilter)}",
//                            color = Color.Gray
//                        )
//                    }
//                } else {
//                    NotFoundMovies(movies)
//                    PagingLoading(movies)
//                    MoviesList(gridColumns, movies, onMovieClick)
//                }
//            }
//        }
//    }
//}

@Composable
private fun SearchContent(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onFilterSelected: (SearchFilter) -> Unit,
    movies: LazyPagingItems<TopRateMovieEntity>?,
    isLoading: Boolean,
    error: String?,
    appendLoading: Boolean,
    appendError: String?,
    gridColumns: Int,
    onMovieClick: (Int) -> Unit
) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                SearchHeader(
                    query = uiState.query,
                    onQueryChange = onQueryChange,
                    onBackClick = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
                FilterChips(
                    selectedFilter = uiState.selectedFilter.ordinal,
                    onFilterSelected = onFilterSelected
                )

                if (uiState.query.isBlank()) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Search movies by ${SearchFilter.fromString(uiState.selectedFilter)}",
                            color = Color.Gray
                        )
                    }
                } else {
                    SearchResultsContent(
                        movies = movies,
                        isLoading = isLoading,
                        error = error,
                        appendLoading = appendLoading,
                        appendError = appendError,
                        gridColumns = gridColumns,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResultsContent(
    movies: LazyPagingItems<TopRateMovieEntity>?,
    isLoading: Boolean,
    error: String?,
    appendLoading: Boolean,
    appendError: String?,
    gridColumns: Int,
    onMovieClick: (Int) -> Unit
) {
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        (movies?.itemCount == 0 || movies == null) && !isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Not found.",
                    color = Color.Gray
                )
            }
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(gridColumns),
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (movies != null) {
                    items(movies.itemCount) { index ->
                        movies[index]?.let { movie ->
                            MovieCard(
                                onClick = { onMovieClick(movie.id) },
                                movie = movie
                            )
                        }
                    }
                }

                if (appendLoading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation()
                        }
                    }
                }

                appendError?.let {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Error: $it",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = MovieStreamingTheme.colors.selectIndicatorColor
            )
        }
        SearchBox(
            query = query,
            onQueryChanged = onQueryChange,
        )
    }
}

@Composable
fun SearchBox(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null
            )
        },
        placeholder = { Text("Search") },
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(start = 4.dp, end = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MovieStreamingTheme.colors.selectIndicatorColor,
            focusedContainerColor = MovieStreamingTheme.colors.selectIndicatorColor,
            focusedTextColor = MovieStreamingTheme.colors.startSliderColor
        )
    )
}

@Composable
fun FilterChips(
    selectedFilter: Int,
    onFilterSelected: (SearchFilter) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(SearchFilter.entries.toTypedArray()) { filter ->
            FilterChip(
                selected = filter.ordinal == selectedFilter,
                onClick = { onFilterSelected(filter) },
                label = { Text(SearchFilter.fromString(filter)) },
                modifier = Modifier
                    .padding(end = 8.dp),
                shape = CircleShape,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MovieStreamingTheme.colors.selectIndicatorColor,
                    labelColor = Color.Gray,
                )
            )
        }
    }
}

private fun getFakePagingData(): PagingData<TopRateMovieEntity> {
    val movies = List(100) { index ->
        TopRateMovieEntity(
            1,
            "Inception",
            "",
            "Dream within a dream",
            8,
            rate = 8.8
        )
    }

    return PagingData.from(movies)
}

@Preview
@Composable
fun PreviewSearchContentSuccess() {
    val pagingData = remember { flowOf(getFakePagingData()) }
    val lazyPagingItems = pagingData.collectAsLazyPagingItems()

    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "something", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = lazyPagingItems,
            isLoading = false,
            error = null,
            appendLoading = false,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewSearchContentAppendLoading() {
    val pagingData = remember { flowOf(getFakePagingData()) }
    val lazyPagingItems = pagingData.collectAsLazyPagingItems()

    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "something", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = lazyPagingItems,
            isLoading = false,
            error = null,
            appendLoading = true,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewSearchContentAppendError() {
    val pagingData = remember { flowOf(getFakePagingData()) }
    val lazyPagingItems = pagingData.collectAsLazyPagingItems()

    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "something", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = lazyPagingItems,
            isLoading = false,
            error = null,
            appendLoading = false,
            appendError = "Network timeout",
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSearchContentInitialPrompt() {
    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = null,
            isLoading = false,
            error = null,
            appendLoading = false,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchContentLoading() {
    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(
                query = "test",
                selectedFilter = SearchFilter.BY_NAME
            ),
            onQueryChange = {},
            onFilterSelected = {},
            movies = null,
            isLoading = true,
            error = null,
            appendLoading = false,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchContentError() {
    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "error", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = null,
            isLoading = false,
            error = "Failed to fetch results",
            appendLoading = false,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchContentNotFoundWorkaround() {
    MovieStreamingTheme {
        SearchContent(
            uiState = SearchUiState(query = "something", selectedFilter = SearchFilter.BY_NAME),
            onQueryChange = {},
            onFilterSelected = {},
            movies = null,
            isLoading = false,
            error = null,
            appendLoading = false,
            appendError = null,
            gridColumns = 3,
            onMovieClick = {}
        )
    }
}