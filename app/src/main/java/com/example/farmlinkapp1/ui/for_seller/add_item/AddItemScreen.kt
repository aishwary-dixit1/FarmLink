package com.example.farmlinkapp1.ui.for_seller.add_item

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.Category
import com.example.farmlinkapp1.model.Item
import com.example.farmlinkapp1.ui.theme.FarmLinkAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddItemScreen(
    navigateBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    val viewModel: AddItemViewModel = viewModel()
    val categories by MongoDB.getAllCategories()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    var categoriesExpanded by remember {
        mutableStateOf(false)
    }

    var chosenCategory by remember {
        mutableStateOf(Category())
    }

    var itemsExpanded by remember {
        mutableStateOf(false)
    }

    val items by MongoDB.getAllItemsByCategoryId(chosenCategory._id)
        .collectAsStateWithLifecycle(initialValue = emptyList())
    var chosenItem by remember {
        mutableStateOf(Item())
    }

    var price by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var showInvalidPriceDialog by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RectangleShape,
            onClick = { categoriesExpanded = !categoriesExpanded }
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Category: ${chosenCategory.title}",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Drop Down"
                    )
                }

                DropdownMenu(
                    expanded = categoriesExpanded,
                    onDismissRequest = { categoriesExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(category.title) },
                            onClick = {
                                chosenCategory = category
                                categoriesExpanded = false
                            }
                        )
                    }
                }
            }
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RectangleShape,
            onClick = { itemsExpanded = !itemsExpanded }
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Item: ${chosenItem.title}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                    )

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Drop Down"
                    )
                }

                DropdownMenu(
                    expanded = itemsExpanded,
                    onDismissRequest = { itemsExpanded = false },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.title) },
                            onClick = {
                                chosenItem = item
                                itemsExpanded = false
                            }
                        )
                    }
                }
            }
        }

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
            label = {
                Text(
                    text = "Price per Kg",
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

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RectangleShape,
            enabled = chosenCategory.title != "" && chosenItem.title != "" && quantity != "" && price != "",
            onClick = {
                if (price.toDouble() < chosenItem.msp) {
                    showInvalidPriceDialog = true
                } else {
                    viewModel.addNewItem(chosenItem._id, quantity.toDouble(), price.toDouble())
                    scope.launch {
                        snackbarHostState.showSnackbar(message = "Item Added Successfully")
                        delay(5000)
                    }
                    navigateBack()
                }
            }
        ) {
            Text(
                text = "Add Item For Sale",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
        }
    }

    if (showInvalidPriceDialog) {
        AlertDialog(
            onDismissRequest = { showInvalidPriceDialog = false },
            confirmButton = {
                Button(onClick = { showInvalidPriceDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Price Below MSP!") },
            text = { Text("Price: $price cannot be less than MSP: ${chosenItem.msp}") },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview1() {
    FarmLinkAppTheme(darkTheme = true) {
        //AddItemScreen({})
    }
}
