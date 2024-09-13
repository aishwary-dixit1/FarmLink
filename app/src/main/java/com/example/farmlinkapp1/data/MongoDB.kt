package com.example.farmlinkapp1.data

import android.location.Location
import android.util.Log
import com.example.farmlinkapp1.model.Buyer
import com.example.farmlinkapp1.model.Category
import com.example.farmlinkapp1.model.ChatMessage
import com.example.farmlinkapp1.model.Item
import com.example.farmlinkapp1.model.Review
import com.example.farmlinkapp1.model.SaleItem
import com.example.farmlinkapp1.model.Seller
import com.example.farmlinkapp1.model.User
import com.example.farmlinkapp1.util.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.annotations.ExperimentalFlexibleSyncApi
import io.realm.kotlin.mongodb.ext.profileAsBsonDocument
import io.realm.kotlin.mongodb.ext.subscribe
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.mongodb.sync.WaitForSync
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import kotlin.math.ceil
import kotlin.math.max

object MongoDB : MongoDBRepository {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm
    private lateinit var userLocation: Location

    var haveNewLocation = false

    init {
        configureRealm()
        //initializeDB()
    }

    override fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user = user,
                schema = setOf(
                    Category::class,
                    Item::class,
                    SaleItem::class,
                    User::class,
                    Review::class,
                    Buyer::class,
                    Seller::class,
                    ChatMessage::class
                )
            )
                .initialSubscriptions { realm ->
                    add(
                        query = realm.query<Category>(),
                        name = "Categories",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<Item>(),
                        name = "Items",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<SaleItem>(),
                        name = "SaleItems",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<Seller>(),
                        name = "Seller",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<Buyer>(),
                        name = "Buyer",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<User>(),
                        name = "User",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<Review>(),
                        name = "Review",
                        updateExisting = true
                    )

                    add(
                        query = realm.query<ChatMessage>(),
                        name = "Chat Message",
                        updateExisting = true
                    )

//                    add(
//                        query = realm.query<User>(query = "ownerId == $0", user.id),
//                        name = "UserData",
//                        updateExisting = true
//                    )
                }
                //.log(LogLevel.ALL)
                .build()

            try {
                realm = Realm.open(config)
            } catch (e: Exception) {
                Log.d("Realm Exception", e.message!!)
            }
        }
    }

    fun getCurrentUser(): User {
        return if (user != null) {
            realm.query<User>("ownerId == $0", user.id).find().first()
        } else User()
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return realm.query<Category>().asFlow().map { it.list }
    }

    override fun getAllItemsByCategoryId(categoryId: ObjectId): Flow<List<Item>> {
        return realm.query<Item>("category._id == $0", categoryId).asFlow().map { it.list }
    }

    override fun getAllSaleItemsByItemId(itemId: ObjectId): Flow<List<SaleItem>> {
        return realm.query<SaleItem>("item._id == $0", itemId).asFlow().map { it.list }
    }

    override fun getItemById(itemId: ObjectId): Item {
        return realm.query<Item>("_id == $0", itemId).first().find()!!
    }

    override fun getCategoryName(categoryId: ObjectId): String {
        return realm.query<Category>("_id == $0", categoryId).find().first().title
    }

    override fun getItemName(itemId: ObjectId): String {
        return realm.query<Item>("_id == $0", itemId).find().first().title
    }

    override fun getItemImageById(itemId: ObjectId): String {
        return realm.query<Item>("_id == $0", itemId).find().first().imageUrl
    }

    override suspend fun addNewSaleItem(
        itemId: ObjectId,
        quantity: Double,
        pricePerKg: Double,
    ) {
        if (user != null) {
            realm.write {
                val item = query<Item>("_id == $0", itemId).find().first()
                val newSaleItem = SaleItem().apply {
                    this.item = findLatest(item)!!
                    quantityInKg = quantity
                    this.pricePerKg = pricePerKg
                    seller = findLatest(getSellerByUserId())
                    ownerId = user.id
                }

                copyToRealm(newSaleItem, UpdatePolicy.ALL)

                val user = query<User>("ownerId == $0", user.id).find().first()
                user.seller!!.itemsListed.add(newSaleItem)
            }
        }
    }

    override fun getSellerByUserId(): Seller {
        return if (user != null) {
            realm.query<User>("ownerId == $0", user.id).find().first().seller!!
        } else Seller()
    }

    override fun userKnown(): Boolean {
        if (user != null) {
            val existingUser = realm.query<User>("ownerId == $0", user.id).find()
            return !existingUser.isEmpty()
        } else return false
    }

    override suspend fun createUser(address: String, phoneNo: String) {
        if (user != null) {
            val customUserData = user.profileAsBsonDocument()
            realm.write {
                if (!userKnown()) {
                    val user = User().apply {
                        ownerId = user.id
                        this.address = address
                        phoneNumber = phoneNo
                        name = customUserData.getString("name").value
                        picture = customUserData.getString("picture").value
                        email = customUserData.getString("email").value
                    }
                    copyToRealm(user, UpdatePolicy.ALL)
                }
            }
        }
    }

    override suspend fun addSellerToUser() {
        if (user != null) {
            realm.write {
                val user = query<User>("ownerId == $0", user.id).find().first()
                if (user.seller == null) {
                    user.seller = Seller().apply {
                        this.user = user
                    }
                }
                copyToRealm(user, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun addBuyerToUser() {
        if (user != null) {
            realm.write {
                val user = query<User>("ownerId == $0", user.id).find().first()
                if (user.buyer == null) {
                    user.buyer = Buyer().apply {
                        this.user = user
                    }
                }

                copyToRealm(user, UpdatePolicy.ALL)
            }
        }
    }

    override fun getAllSaleItemsForSeller(): Flow<List<SaleItem>> {
//        return if (user != null) {
//            realm.query<SaleItem>("ownerId == $0", user.id).asFlow().map { it.list }
//        } else emptyList<List<SaleItem>>().asFlow()

        if (user != null) {
            val x = realm.query<SaleItem>("ownerId == $0", user.id).asFlow().map { it.list }
            return x
        } else {
            return emptyList<List<SaleItem>>().asFlow()
        }
    }

    override fun getUser(): User {
        return if (user == null) User()
        else realm.query<User>("ownerId == $0", user.id).find().first()
    }

    override fun getSellerNameFromSaleItemId(saleItemId: ObjectId): String {
        return realm.query<User>("seller.user._id == $0", saleItemId).find()
            .first().seller?.user?.name ?: "Item"
    }

    override suspend fun deleteSaleItem(saleItem: SaleItem) {
        realm.write {
            delete(findLatest(saleItem)!!)
        }
    }

    override suspend fun markSale(saleItem: SaleItem, quantity: Double, price: Double) {
        realm.write {
            if (user != null) {
                val soldItem = SaleItem().apply {
                    quantityInKg = quantity
                    pricePerKg = price
                    ownerId = user.id
                    active = false
                    seller = findLatest(saleItem.seller!!)
                    item = findLatest(saleItem.item!!)
                }

                val user = query<User>("ownerId == $0", user.id).find().first()
                user.seller!!.itemsListed.add(soldItem)

                copyToRealm(soldItem, UpdatePolicy.ALL)

                if (quantity == saleItem.quantityInKg) {
                    delete(findLatest(saleItem)!!)
                } else {
                    findLatest(saleItem)?.apply {
                        quantityInKg -= quantity
                    }
                }
            }
        }
    }

//    override fun getItemReview(saleItemId: ObjectId): Flow<List<Review>> {
//        //return realm.query<SaleItem>("._id == $0", saleItemId).asFlow().
//    }

    override fun getSaleItemById(saleItemId: ObjectId): SaleItem {
        return realm.query<SaleItem>("_id == $0", saleItemId).find().first()
    }

    fun getUserPhoneNumber(ownerId: String): String {
        return realm.query<User>("ownerId == $0", ownerId).find().first().phoneNumber
    }

    override fun getUserByOwnerId(ownerId: String): User {
        return if (user != null) {
            realm.query<User>("ownerId == $0", ownerId).find().first()
        } else User()
    }

    fun searchForItem(title: String): Item {
        //val regex = Regex(title, RegexOption.IGNORE_CASE)
        return realm.query<Item>("title == $0", title).find().first()
    }

    @OptIn(ExperimentalFlexibleSyncApi::class)
    override suspend fun postReview(saleItemId: ObjectId, newRating: Int, userReview: String) {
        val reviewQuery = realm.query<Review>()
        reviewQuery.subscribe("Review", true, WaitForSync.ALWAYS)
        realm.write {
            val saleItem = query<SaleItem>("_id == $0", saleItemId).find().first()
            val newSubtask = copyToRealm(Review().apply {
                rating = newRating
                reviewText = userReview
                this.saleItem = findLatest(saleItem)
                reviewedBy = findLatest(getCurrentUser())
            }) // Create and copy a new Subtask
            saleItem.reviews.add(newSubtask) // Add the new subtask to the RealmList
//            copyToRealm(review, UpdatePolicy.ALL)
//            saleItem.reviews.add(findLatest(review)!!)
        }
    }

    override suspend fun updateUserRating(newRating: Int) {
        realm.write {
            if (user != null) {
                val user = query<User>("ownerId == $0", user.id).find().first()
                findLatest(user)?.seller!!.apply {
                    ratingCount++
                    ratings =
                        max(5.0, ceil(((ratings + newRating) * 1 / ratingCount).toDouble())).toInt()
                }
                copyToRealm(user, UpdatePolicy.ALL)
            }
        }
    }

    override fun getAllReviewsOfSaleItem(saleItemId: ObjectId): Flow<List<Review>> {
        return realm.query<Review>("saleItem._id == $0", saleItemId).asFlow().map { it.list }
    }

    @OptIn(ExperimentalFlexibleSyncApi::class)
    suspend fun sendMessage(messageText: String, chatRoomId: String) {
        val reviewQuery = realm.query<ChatMessage>()
        reviewQuery.subscribe("Chat Message", true, WaitForSync.ALWAYS)

        val newMessage = ChatMessage().apply {
            message = messageText
            this.senderId = user!!.id
            this.chatRoomId = chatRoomId
            timestamp = System.currentTimeMillis()
        }

        realm.write {
            copyToRealm(newMessage)
        }
    }

    fun getChatMessages(): Flow<List<ChatMessage>> {
        return realm.query<ChatMessage>("chatRoomId == $0", "12345").asFlow().map { it.list }
    }

    fun getOwnerId(): String {
        return user!!.id
    }

    fun setUserLocation(location: Location) {
        userLocation = location
    }

    suspend fun storeLocationData() {
        val user = realm.query<User>("ownerId == $0", user!!.id).find().first()

        realm.write {
            findLatest(user)?.apply {
                latitude = userLocation.latitude
                longitude = userLocation.longitude
            }
        }
    }

    fun newDataEntered(): Boolean {
        return ::userLocation.isInitialized
    }
}