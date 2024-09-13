package com.example.farmlinkapp1.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DataCard(
    title: String,
    imageUrl: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onCardClick,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {

            AsyncImageLoader(imageUrl = imageUrl, title = title, modifier = modifier)

            //if (imageLoadSuccessful) {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black)
                        )
                    )
            )

            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                color = Color.White,
                style = textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
//}

@Preview(showSystemUi = true)
@Composable
fun DataCardPreview(modifier: Modifier = Modifier) {
    DataCard("VEGETABLES", "", {})
}