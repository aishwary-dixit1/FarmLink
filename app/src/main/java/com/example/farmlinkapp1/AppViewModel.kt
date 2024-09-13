package com.example.farmlinkapp1

import androidx.lifecycle.ViewModel
import com.example.farmlinkapp1.data.MongoDB
import org.mongodb.kbson.ObjectId

class AppViewModel : ViewModel() {

    fun getCategoryName(categoryId: ObjectId) = MongoDB.getCategoryName(categoryId)

    fun getItemName(itemId: ObjectId) = MongoDB.getItemName(itemId)
}