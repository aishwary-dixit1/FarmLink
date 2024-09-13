package com.example.farmlinkapp1.ui.for_seller.seller_dashboard

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmlinkapp1.common.AsyncImageLoader
import com.example.farmlinkapp1.common.RatingStars
import com.example.farmlinkapp1.common.SaleItemCard
import com.example.farmlinkapp1.model.SaleItem
import com.example.farmlinkapp1.model.User
import com.example.farmlinkapp1.ui.theme.FarmLinkAppTheme

//val user = User().apply {
//    name = "Abhinav Srivastava"
//    email = "abhi555sri@gmail.com"
//    picture =
//        "https://lh3.googleusercontent.com/a/ACg8ocJDtkrFt3JNFBEnSz8YlkcY9SfNS84u7QVtvRt1CauUD58-dt0=s96-c"
//    address = "2, Downing Street, London, UK"
//    phoneNumber = "9555909041"
//    seller = Seller().apply {
//        ratings = 3
//    }
//}
//
//val saleItem = SaleItem().apply {
//    quantityInKg = 50.5
//    pricePerKg = 15.6
//    distance = 10.5
//    active = true
//    seller = user.seller
//    item = Item().apply {
//        title = "Orange"
//    }
//}

@Composable
fun SellerDashboardScreen(
    user: User,
    onNavigateToSoldItems: () -> Unit,
    onNavigateToActiveItems: () -> Unit,
    onNavigateToAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
//    user.seller!!.user = user
//    user.seller!!.itemsListed = realmListOf(saleItem)

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Greeting(user)
        UserInfoHeader(user)
        Spacer(Modifier.height(8.dp))

        val noOfSaleItems = user.seller!!.itemsListed.size
        ActiveItemsSummary(
            onNavigateToActiveItems = onNavigateToActiveItems,
            onNavigateToAddItem = onNavigateToAddItem,
            saleItem = if (noOfSaleItems > 0) user.seller!!.itemsListed[noOfSaleItems - 1] else null
        )

        val soldItems = user.seller!!.itemsListed.filter { !it.active }
        SoldItemsSummary(
            onNavigateToSoldItems = onNavigateToSoldItems,
            saleItem = if (soldItems.isNotEmpty()) soldItems[0] else null
        )
    }
}

@Composable
fun Greeting(user: User) {
    Text(
        text = "Welcome, ${user.name.split(" ")[0]}!",
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 26.sp),
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun UserInfoHeader(user: User, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImageLoader(
            imageUrl = user.picture,
            modifier = modifier
                .height(100.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 12.dp)
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 12.dp)
            )

            RatingStars(
                ratings = user.seller!!.ratings,
                modifier = Modifier.padding(top = 12.dp),
                size = 32.dp
            )
        }
    }
}

@Composable
fun ActiveItemsSummary(
    onNavigateToActiveItems: () -> Unit,
    onNavigateToAddItem: () -> Unit,
    saleItem: SaleItem?,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier
    ) {
        Column {
            Text(
                text = "On Sale",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )

            if (saleItem == null) {
                Text(
                    text = "No Items!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            } else {
                SaleItemCard(
                    saleItem = saleItem,
                    title = saleItem.item!!.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            OutlinedButton(
                onClick = onNavigateToActiveItems,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "View All", fontSize = 18.sp)
            }

            Button(
                onClick = onNavigateToAddItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp, 8.dp, 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add New Entry", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun SoldItemsSummary(
    onNavigateToSoldItems: () -> Unit,
    saleItem: SaleItem?,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column {
            Text(
                text = "Sold",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold
            )

            if (saleItem == null) {
                Text(
                    text = "No Items!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            } else {
                SaleItemCard(
                    saleItem = saleItem,
                    title = saleItem.item!!.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            OutlinedButton(
                onClick = onNavigateToSoldItems,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "View All", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    FarmLinkAppTheme {
        SellerDashboardScreen(User(), {}, {}, {})
    }
}