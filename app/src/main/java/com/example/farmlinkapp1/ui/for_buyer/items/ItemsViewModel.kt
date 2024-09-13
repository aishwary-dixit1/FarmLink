package com.example.farmlinkapp1.ui.for_buyer.items

import androidx.lifecycle.ViewModel
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.Item
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

class ItemsViewModel : ViewModel() {

    fun getAllItemsByCategory(categoryId: ObjectId) : Flow<List<Item>> = MongoDB.getAllItemsByCategoryId(categoryId)
}