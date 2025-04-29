package com.moviestreaming.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviestreaming.R
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun GenreItem(genre: String) {
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

@Preview
@Composable
fun GenreItemPreview() {
    MovieStreamingTheme {
        GenreItem(
            genre = "Action"
        )
    }
}