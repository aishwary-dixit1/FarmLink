package com.example.farmlinkapp1.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.farmlinkapp1.R

@Composable
fun ClickableRatingStars(
    current: MutableIntState,
    size: Dp,
    modifier: Modifier = Modifier
) {

    Row(modifier = modifier.fillMaxWidth()) {
        for (i in 1..5) {
            IconButton(
                onClick = { current.intValue = i },
            ) {
                Icon(
                    painter = painterResource(id = if (i <= current.intValue) R.drawable.filled_star else R.drawable.outlined_star),
                    contentDescription = "Star",
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(size)
                )
            }
        }
    }
}