package com.example.farmlinkapp1.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class User : RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var ownerId: String = ""
    var name: String = ""
    var email: String = ""
    var picture: String = ""
    var address: String = ""
    var phoneNumber: String = ""
    var buyer: Buyer? = null
    var seller: Seller? = null

    var latitude: Double = 0.0
    var longitude: Double = 0.0
}