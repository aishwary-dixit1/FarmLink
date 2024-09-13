package com.example.farmlinkapp1.ui.for_seller.active_items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.common.SaleItemCard
import com.example.farmlinkapp1.model.SaleItem
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

@Composable
fun ActiveItemsScreen(
    allSaleItems: Flow<List<SaleItem>>,
    navigateToSaleItemDetails: (ObjectId) -> Unit,
    //snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    val allItems by allSaleItems.collectAsStateWithLifecycle(initialValue = emptyList())
    val activeItems = allItems.filter { it.active }

    LazyColumn(modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
        items(activeItems) { saleItem ->
            ActiveItemCard(saleItem, navigateToSaleItemDetails)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ActiveItemCard(
    saleItem: SaleItem,
    navigateToSaleItemDetails: (ObjectId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ActiveItemsViewModel = viewModel()
    var expanded by remember {
        mutableStateOf(false)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var showDeleteConfirmationDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SaleItemCard(
            saleItem = saleItem,
            title = saleItem.item!!.title,
            onClick = { expanded = !expanded }
        )

        if (expanded) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(onClick = {
                        showDialog = true
                    }, modifier = Modifier.weight(1f)) {
                        Text("Mark as Sold")
                    }

                    OutlinedButton(
                        onClick = {
                            showDeleteConfirmationDialog = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete Item")
                    }
                }

                OutlinedButton(
                    onClick = { navigateToSaleItemDetails(saleItem._id) }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text("Show Details")
                }
            }
        }
    }

    var quantity by remember {
        mutableStateOf(saleItem.quantityInKg.toString())
    }

    var price by remember {
        mutableStateOf(saleItem.pricePerKg.toString())
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.markSale(saleItem, quantity.toDouble(), price.toDouble())
                    showDialog = false
                    expanded = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text("Confirm Sale") },
            text = {
                Column() {
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = {
                            Text(
                                text = "Quantity in Kg",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text(text = "Price per Kg") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        )
    }

    if (showDeleteConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteSaleItem(saleItem)
                        showDeleteConfirmationDialog = false
                        expanded = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmationDialog = false }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Delete?")
            },
            text = {
                Text(text = "Are you sure you want to delete this item?")
            }
        )
    }
}
