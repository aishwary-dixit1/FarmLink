package com.example.farmlinkapp1.ui.for_seller.sold_items

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.farmlinkapp1.common.SaleItemCard
import com.example.farmlinkapp1.model.SaleItem
import kotlinx.coroutines.flow.Flow

@Composable
fun SoldItemsScreen(
    allSaleItems: Flow<List<SaleItem>>,
    modifier: Modifier = Modifier
) {
    val allItems by allSaleItems.collectAsStateWithLifecycle(initialValue = emptyList())
    val soldItems = allItems.filter { !it.active }

    LazyColumn(modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)) {
        items(soldItems) { saleItem ->
            SaleItemCard(saleItem = saleItem, title = saleItem.item!!.title)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}