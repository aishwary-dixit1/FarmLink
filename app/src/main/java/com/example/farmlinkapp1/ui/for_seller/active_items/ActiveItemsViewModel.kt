package com.example.farmlinkapp1.ui.for_seller.active_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.SaleItem
import kotlinx.coroutines.launch

class ActiveItemsViewModel : ViewModel() {
    fun deleteSaleItem(saleItem: SaleItem) = viewModelScope.launch {
        MongoDB.deleteSaleItem(saleItem)
    }

    fun markSale(saleItem: SaleItem, quantity: Double, price: Double) = viewModelScope.launch {
        MongoDB.markSale(saleItem, quantity, price)
    }
}