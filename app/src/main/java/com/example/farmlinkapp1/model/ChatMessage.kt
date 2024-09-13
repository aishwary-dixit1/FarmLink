package com.example.farmlinkapp1.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ChatMessage : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var message: String = ""
    var senderId: String = ""
    var chatRoomId: String = ""
    var timestamp: Long = 0
}
