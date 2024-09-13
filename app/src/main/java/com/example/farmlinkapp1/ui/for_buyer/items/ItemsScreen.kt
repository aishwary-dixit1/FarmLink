package com.example.farmlinkapp1.ui.for_buyer.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.common.DataCard
import com.example.farmlinkapp1.common.SearchFeature
import org.mongodb.kbson.ObjectId

@Composable
fun ItemsScreen(
    categoryId: ObjectId,
    onClick: (ObjectId) -> Unit,
    onSearchRequest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemsViewModel: ItemsViewModel = viewModel()
    val itemsList by itemsViewModel.getAllItemsByCategory(categoryId).collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    Column(modifier = modifier) {
        SearchFeature(onClick = { onSearchRequest(it) })

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(itemsList) { item ->
                DataCard(
                    title = item.title,
                    imageUrl = item.imageUrl,
                    onCardClick = { onClick(item._id) },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
        }
    }
}