package com.example.farmlinkapp1.navigation

import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

//auth
@Serializable
data object Authentication  //route for nested navigation

@Serializable
data object SignIn

@Serializable
data object UserType

@Serializable
data object UserDetails

//for seller
@Serializable
data object SellerApp //route for nested navigation

@Serializable
data object SellerDashboard

@Serializable
data object SoldItems

@Serializable
data object ActiveItems

@Serializable
data object AddItem

//for buyers
@Serializable
data object BuyerApp //route for nested navigation


@Serializable
data object Home

@Serializable
data class Items(val categoryId: ObjectId)

@Serializable
data class SellerInventory(val itemId: ObjectId)

@Serializable
data class SellerDetails(val saleItemId: ObjectId)

@Serializable
data class Chat(val sellerId: String)