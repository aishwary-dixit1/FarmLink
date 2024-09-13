package com.example.farmlinkapp1.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Review : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var rating: Int = 0
    var reviewText: String? = null
    var saleItem: SaleItem? = null
    var reviewedBy: User? = null
}