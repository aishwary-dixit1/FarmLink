package com.example.farmlinkapp1.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.farmlinkapp1.R
import kotlin.math.max

@Composable
fun RatingStars(
    ratings: Int,
    size: Dp,
    modifier: Modifier = Modifier
) {
    if (ratings == 0) {
        Text("No Ratings Yet!")
    } else {
        Row(modifier = modifier) {
            repeat(ratings) {
                Icon(
                    painter = painterResource(id = R.drawable.filled_star),
                    contentDescription = "Star",
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(size)
                )
            }

            repeat(max(0, 5 - ratings)) {
                Icon(
                    painter = painterResource(id = R.drawable.outlined_star),
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