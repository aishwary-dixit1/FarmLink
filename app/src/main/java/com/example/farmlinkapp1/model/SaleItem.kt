package com.example.farmlinkapp1.model

import com.example.farmlinkapp1.util.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.Instant

class SaleItem : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var ownerId: String = ""
    var item: Item? = null
    var seller: Seller? = null
    var quantityInKg: Double = 0.0
    var pricePerKg: Double = 0.0
    var distance: Double = 0.0
    var active: Boolean = true
    var date: RealmInstant = Instant.now().toRealmInstant()
    var reviews: RealmList<Review> = realmListOf()
}