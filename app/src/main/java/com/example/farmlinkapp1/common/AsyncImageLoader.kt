package com.example.farmlinkapp1.common

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.farmlinkapp1.R

@Composable
fun AsyncImageLoader(
    imageUrl: String,
    modifier: Modifier = Modifier,
    title: String = ""
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = title,
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.error),
        placeholder = painterResource(id = R.drawable.loading2),
        onError = { Log.d("error", it.result.throwable.message.toString())}
    )
}