package com.example.farmlinkapp1.ui.for_buyer.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.common.DataCard
import com.example.farmlinkapp1.common.SearchFeature
import org.mongodb.kbson.ObjectId

@Composable
fun HomeScreen(
    onClick: (ObjectId) -> Unit,
    onSearchRequest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = viewModel()
    val categories by viewModel.getAllCategories().collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchFeature(onClick = { onSearchRequest(it) })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            categories.forEach { category ->
                DataCard(
                    title = category.title,
                    imageUrl = category.imageUrl,
                    onCardClick = {
                        onClick(category._id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }
        }
    }
}
