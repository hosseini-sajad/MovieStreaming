package com.moviestreaming.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviestreaming.R
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun SearchScreenRoute() {
    SearchScreen()
}

@Composable
fun SearchScreen() {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SearchHeader(
                onBackClick = {}
            )
        }
    }
}

@Composable
fun SearchHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = MovieStreamingTheme.colors.selectIndicatorColor
            )
        }
        SearchBox(
            query = "",
            onQueryChanged = {},
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
            .height(45.dp)
            .padding(start = 4.dp, end = 16.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MovieStreamingTheme.colors.selectIndicatorColor
        )
    )
}

@Preview
@Composable
fun SearchScreenPreview() {
    MovieStreamingTheme {
        SearchScreen()
    }
}