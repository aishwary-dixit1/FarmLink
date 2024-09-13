package com.example.farmlinkapp1.ui.for_buyer.seller_details

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.Review
import com.example.farmlinkapp1.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class SellerDetailsViewModel : ViewModel() {

    fun getSaleItemById(saleItemId: ObjectId) = MongoDB.getSaleItemById(saleItemId)
    fun getUserByOwnerId(ownerId: String): User = MongoDB.getUserByOwnerId(ownerId)

    //fun getItemReviews(saleItemId: ObjectId): Flow<List<Review>> = MongoDB.getItemReviews(saleItemId)

    fun raisePhoneCallIntent(phoneNo: String, context: Context, activity: Activity) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
            makePhoneCall(phoneNo, context)
        } else {
            makePhoneCall(phoneNo, context)
        }
    }

    private fun makePhoneCall(
        phoneNo: String,
        context: Context
    ) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:+91${phoneNo}")
        }

        context.startActivity(intent)
    }

    fun postReview(saleItemId: ObjectId, newRating: Int, userReview: String) {
        viewModelScope.launch {
            MongoDB.postReview(saleItemId, newRating, userReview)
        }
    }

    fun updateUserRating(newRating: Int) {
        viewModelScope.launch {
            MongoDB.updateUserRating(newRating)
        }
    }

    fun getAllReviews(saleItemId: ObjectId): Flow<List<Review>> {
        return MongoDB.getAllReviewsOfSaleItem(saleItemId)
    }
}
