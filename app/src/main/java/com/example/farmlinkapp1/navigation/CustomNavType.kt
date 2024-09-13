package com.example.farmlinkapp1.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.mongodb.kbson.ObjectId

object CustomNavType {

    val objectIdType = object : NavType<ObjectId>(isNullableAllowed = false) {
        override fun put(bundle: Bundle, key: String, value: ObjectId) {
            return bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: ObjectId): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun get(bundle: Bundle, key: String): ObjectId? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ObjectId {
            return Json.decodeFromString(Uri.decode(value))
        }
    }
}
