package com.moviestreaming.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.moviestreaming.R
import com.moviestreaming.ui.theme.MovieStreamingTheme

@Composable
fun CastCard(
    castImage: String?,
    castName: String?
) {
    val isInPreview = LocalInspectionMode.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isInPreview) {
            Image(
                painter = painterResource(R.drawable.tenet),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
        } else {
            AsyncImage(
                model = castImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.ic_image_error),
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = castName ?: "",
            fontFamily = FontFamily(Font(R.font.helvetica)),
            fontSize = 14.sp,
            color = MovieStreamingTheme.colors.titleColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(100.dp)
                .padding(top = 10.dp)
        )
    }
}

@Preview
@Composable
private fun CastCardPreview() {
    MovieStreamingTheme {
        CastCard(
            castImage = "",
            castName = "Tom Hardy"
        )
    }
}
