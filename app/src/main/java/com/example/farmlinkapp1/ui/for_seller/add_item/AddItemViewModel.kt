package com.example.farmlinkapp1.ui.for_seller.add_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.Item
import com.example.farmlinkapp1.model.SaleItem
import com.example.farmlinkapp1.util.toRealmInstant
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.time.Instant

class AddItemViewModel : ViewModel() {

    fun constructSaleItem(
        item: Item,
        quantity: Double,
        pricePerKg: Double,
    ) : SaleItem {
        return SaleItem().apply {
            this.item = item
            this.quantityInKg = quantity
            this.pricePerKg = pricePerKg
            date = Instant.now().toRealmInstant()
            seller = MongoDB.getSellerByUserId()
        }
    }

    fun addNewItem(
        itemId: ObjectId,
        quantity: Double,
        pricePerKg: Double,
    ) {
        viewModelScope.launch {
            MongoDB.addNewSaleItem(itemId, quantity, pricePerKg)
        }
    }
}