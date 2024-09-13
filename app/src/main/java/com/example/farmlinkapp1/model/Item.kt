package com.example.farmlinkapp1.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Item : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var title: String = ""
    var imageUrl: String = ""
    var msp: Int = 0
    var category: Category? = null
    var saleItems: RealmList<SaleItem> = realmListOf()
}