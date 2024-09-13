package com.example.farmlinkapp1.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Buyer : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var itemsBought: RealmList<SaleItem> = realmListOf()
    var user: User? = null
}