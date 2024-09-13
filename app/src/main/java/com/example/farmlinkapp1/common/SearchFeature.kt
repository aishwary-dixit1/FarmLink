package com.example.farmlinkapp1.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.farmlinkapp1.R

@Composable
fun SearchFeature(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    padding: Dp = 8.dp,
) {
    var showVoiceSearch by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding),
        value = result,
        onValueChange = { result = it },
        shape = CircleShape,
        placeholder = { Text("Search...") },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (result.isNotEmpty()) {
                IconButton(onClick = {
                    onClick(result)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            } else {
                IconButton(onClick = { showVoiceSearch = true }) {
                    Icon(
                        painter = painterResource(R.drawable.filled_mic),
                        contentDescription = "Mic"
                    )
                }
            }
        }
    )

    if (showVoiceSearch) {
        VoiceTypingFeature(onResult = { text ->
            result = text
            showVoiceSearch = false
        }
        )
    }
}

@Preview
@Composable
private fun SearchFeaturePreview() {
    //SearchFeature("", {})
}