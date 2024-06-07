package com.dmoyahur.moviesapp.common.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dmoyahur.moviesapp.common.R

@Composable
fun ImageCoil(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    @DrawableRes placeholder: Int = R.drawable.poster_placeholder,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String?
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .error(placeholder)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}