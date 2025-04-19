package com.moviestreaming.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.moviestreaming.R
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun MovieCard(modifier: Modifier = Modifier, movie: TrendingEntity) {
    Box(
        modifier = modifier
            .background(
                color = MovieStreamingTheme.colors.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .width(130.dp)
            .wrapContentSize()
    ) {

        Column {
            AsyncImage(
                model = movie.image,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 130.dp, height = 160.dp),
            )
            movie.title?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    maxLines = 1,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.helvetica)),
                    color = MovieStreamingTheme.colors.titleColor
                )
            }
            Text(
                text = "Action",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                maxLines = 1,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.helvetica)),
                color = MovieStreamingTheme.colors.titleColor
            )
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp)
                )
                Text(
                    text = "7/10",
                    color = MovieStreamingTheme.colors.titleColor,
                    modifier = Modifier
                        .padding(start = 5.dp),
                    style = TextStyle(
                        lineHeight = 0.sp,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    MovieStreamingTheme {
        val movie = TrendingEntity(
            id = 1,
            title = "Tent",
            image = "https://image.tmdb.org/t/p/w500/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg",
            mediaType = "movie"
        )
        MovieCard(movie = movie)
    }
}