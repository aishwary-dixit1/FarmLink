package com.example.farmlinkapp1.ui.for_buyer.seller_details

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.common.AsyncImageLoader
import com.example.farmlinkapp1.common.ClickableRatingStars
import com.example.farmlinkapp1.common.RatingStars
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.util.getDistance
import org.mongodb.kbson.ObjectId

@Composable
fun SellerDetailsScreen(
    saleItemId: ObjectId,
    activity: Activity,
    navigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel: SellerDetailsViewModel = viewModel()
    val scrollState = rememberScrollState()

    val saleItem = viewModel.getSaleItemById(saleItemId)
    val user = viewModel.getUserByOwnerId(saleItem.ownerId)

    var showReviewDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImageLoader(
                imageUrl = user.picture,
                modifier = Modifier
                    .height(100.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                RatingStars(ratings = user.seller!!.ratings, size = 36.dp)
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailCard(
                    label = "Quantity",
                    value = "${saleItem.quantityInKg}kg",
                    modifier = Modifier.weight(1f)
                )

                DetailCard(
                    label = "Quoted Price",
                    value = "₹${saleItem.pricePerKg}/kg",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color.Black),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Text(
                    text = "Location: ${user.address}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Text(
                    text = "Distance: ${getDistance(
                        lat1 = saleItem.seller?.user?.latitude,
                        long1 = saleItem.seller?.user?.longitude,
                        lat2 = MongoDB.getCurrentUser().latitude,
                        long2 = MongoDB.getCurrentUser().longitude
                    )
                    }",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { navigateToChat(user.ownerId) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Message"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Message", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    viewModel.raisePhoneCallIntent(user.phoneNumber, context, activity)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Call", fontSize = 20.sp)
            }
        }

        OutlinedButton(
            onClick = {
                showReviewDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add a Review", fontSize = 22.sp)
        }

        Text(
            text = "Reviews:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val reviews by viewModel.getAllReviews(saleItemId)
            .collectAsStateWithLifecycle(initialValue = emptyList())

        Column {
            reviews.forEach { review ->
                Card(modifier = Modifier.padding(bottom = 8.dp)) {
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AsyncImageLoader(
                                imageUrl = review.reviewedBy!!.picture,
                                modifier = Modifier
                                    .height(48.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = review.reviewedBy?.name!!,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        RatingStars(ratings = review.rating, size = 32.dp)

                        Text(
                            text = review.reviewText ?: "No Review",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Contact Buttons fixed at the bottom
//        Spacer(modifier = Modifier.weight(1f))

    }

    var userReview by remember {
        mutableStateOf("")
    }

    val currentUser = remember {
        mutableIntStateOf(0)
    }
    val currentItem = remember {
        mutableIntStateOf(0)
    }

    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.postReview(saleItem._id, currentItem.intValue, userReview)
                    viewModel.updateUserRating(currentUser.intValue)
                    showReviewDialog = false
                }) {
                    Text("Post")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showReviewDialog = false
                }) {
                    Text("Cancel")
                }
            },
            title = {
                Text(
                    "Leave a Review",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Rate the User", fontSize = 22.sp)
                    ClickableRatingStars(current = currentUser, size = 28.dp)

                    Text("Rate the Item: ${saleItem.item?.title}", fontSize = 22.sp)
                    ClickableRatingStars(current = currentItem, size = 28.dp)

                    Text(text = "Review:", fontSize = 22.sp)
                    OutlinedTextField(
                        value = userReview,
                        onValueChange = { userReview = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        maxLines = 8
                    )
                }
            }
        )
    }
}

@Composable
fun DetailCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
