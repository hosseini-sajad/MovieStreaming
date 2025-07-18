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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.moviestreaming.R
import com.moviestreaming.core.component.MovieCard
import com.moviestreaming.domain.usecase.SearchFilter
import com.moviestreaming.ui.home.LoadingAnimation
import com.moviestreaming.ui.theme.MovieStreamingTheme
import kotlinx.coroutines.flow.emptyFlow

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
    val movies = uiState.searchResults.collectAsLazyPagingItems()
    
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
                
                // Show loading indicator
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                
                // Show error if any
                uiState.error?.let { error ->
                    Text(
                        text = error,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridColumns),
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies.itemCount) { index ->
                        movies[index]?.let { movie ->
                            MovieCard(
                                onClick = { onMovieClick(movie.id) },
                                movie = movie
                            )
                        }
                    }

                    if (movies.loadState.append is LoadState.Loading) {
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

                    movies.loadState.append.let { loadState ->
                        if (loadState is LoadState.Error) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = "Error loading more movies: ${loadState.error.localizedMessage}",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
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

@Preview
@Composable
fun SearchScreenPreview() {
    MovieStreamingTheme {
        SearchScreen(
            uiState = SearchUiState(
                query = "Avengers",
                selectedFilter = SearchFilter.BY_NAME,
                searchResults = emptyFlow(),
                isLoading = false
            ),
            onQueryChange = {},
            onFilterSelected = {},
            onMovieClick = {}
        )
    }
}