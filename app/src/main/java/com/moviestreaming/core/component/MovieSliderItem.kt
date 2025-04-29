package com.moviestreaming.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Image
import coil3.compose.AsyncImage
import com.moviestreaming.R
import com.moviestreaming.data.model.TrendingEntity
import com.moviestreaming.ui.theme.MovieStreamingTheme
import com.moviestreaming.utils.getImageUrl


@Composable
fun MovieSliderItem(
    modifier: Modifier = Modifier,
    movie: TrendingEntity,
    pagerState: PagerState
) {
    val isInPreview = LocalInspectionMode.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        if (isInPreview) {
            Image(
                painter = painterResource(R.drawable.tenet),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        } else {
            AsyncImage(
                model = getImageUrl(movie.image),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_image_error),
                modifier = modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MovieStreamingTheme.colors.startSliderColor,
                            MovieStreamingTheme.colors.centerSliderColor,
                            MovieStreamingTheme.colors.endSliderColor
                        ),
                        startY = 100f,
                        endY = 0f
                    )
                )
                .padding(bottom = 8.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            movie.title?.let {
                Text(
                    text = it,
                    color = MovieStreamingTheme.colors.titleColor,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration)
                            MovieStreamingTheme.colors.selectIndicatorColor
                        else
                            MovieStreamingTheme.colors.unselectIndicatorColor
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieSliderItemPreview() {
    MovieStreamingTheme {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { 7 }
        )
        MovieSliderItem(
            movie = TrendingEntity(
                id = 1,
                title = "Dune: Part Two",
                image = "",
                mediaType = "movie"
            ),
            pagerState = pagerState
        )
    }
}
