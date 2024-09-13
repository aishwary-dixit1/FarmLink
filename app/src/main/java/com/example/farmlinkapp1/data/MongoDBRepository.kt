package com.example.farmlinkapp1.data

import com.example.farmlinkapp1.model.Category
import com.example.farmlinkapp1.model.Item
import com.example.farmlinkapp1.model.Review
import com.example.farmlinkapp1.model.SaleItem
import com.example.farmlinkapp1.model.Seller
import com.example.farmlinkapp1.model.User
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface MongoDBRepository {

    suspend fun createUser(address: String, phoneNo: String)
    fun configureRealm()
    fun getAllCategories() : Flow<List<Category>>
    fun getAllItemsByCategoryId(categoryId: ObjectId) : Flow<List<Item>>
    fun getAllSaleItemsByItemId(itemId: ObjectId) : Flow<List<SaleItem>>
    fun getItemById(itemId: ObjectId) : Item
    fun getCategoryName(categoryId: ObjectId): String
    fun getItemName(itemId: ObjectId): String

    fun getItemImageById(itemId: ObjectId) : String

    suspend fun addNewSaleItem(itemId: ObjectId, quantity: Double, pricePerKg: Double)
    fun getSellerByUserId(): Seller

    suspend fun addSellerToUser()
    suspend fun addBuyerToUser()

    fun getAllSaleItemsForSeller() : Flow<List<SaleItem>>
    fun getUser(): User
    fun userKnown(): Boolean
    fun getSellerNameFromSaleItemId(saleItemId: ObjectId): String

    suspend fun deleteSaleItem(saleItem: SaleItem)
    suspend fun markSale(saleItem: SaleItem, quantity: Double, price: Double)

    //fun getSellerByOwnerId(saleItemId: ObjectId) : User
    fun getSaleItemById(saleItemId: ObjectId) : SaleItem
    //fun getItemReview(saleItemId: ObjectId) : Flow<List<Review>>
    fun getUserByOwnerId(ownerId: String) : User

    suspend fun postReview(saleItemId: ObjectId, newRating: Int, userReview: String)
    suspend fun updateUserRating(newRating: Int)

    fun getAllReviewsOfSaleItem(saleItemId: ObjectId): Flow<List<Review>>
}