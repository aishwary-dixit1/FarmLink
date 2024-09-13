package com.example.farmlinkapp1.navigation

import android.app.Activity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.farmlinkapp1.AppViewModel
import com.example.farmlinkapp1.common.AppScaffold
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.ui.for_buyer.chat.ChatScreen
import com.example.farmlinkapp1.ui.for_buyer.home.HomeScreen
import com.example.farmlinkapp1.ui.for_buyer.items.ItemsScreen
import com.example.farmlinkapp1.ui.for_buyer.saleItems_list.SaleItemsScreen
import com.example.farmlinkapp1.ui.for_buyer.seller_details.SellerDetailsScreen
import org.mongodb.kbson.ObjectId
import kotlin.reflect.typeOf

fun NavGraphBuilder.buyerApp(
    activity: Activity,
    navController: NavHostController
) {
    navigation<BuyerApp>(startDestination = Home) {
        home(navController)
        items(navController)
        sellerInventory(navController)
        sellerDetails(activity, navController)
        chat(navController)
    }
}

fun NavGraphBuilder.home(navController: NavHostController) {
    composable<Home> {
        AppScaffold(
            currentScreenTitle = "FarmLink",
            canNavigateUp = false,
            onNavigateUp = { navController.popBackStack() },
        ) { mod, _ ->

            HomeScreen(
                modifier = mod,
                onClick = { categoryId ->
                    navController.navigate(Items(categoryId = categoryId))
                },
                onSearchRequest = {
                    navController.navigate(SellerInventory(MongoDB.searchForItem(it)._id))
                }
            )
        }
    }
}

fun NavGraphBuilder.items(navController: NavHostController) {
    composable<Items>(
        typeMap = mapOf(
            typeOf<ObjectId>() to CustomNavType.objectIdType
        )
    ) { backStackEntry ->
        val appViewModel: AppViewModel = viewModel()
        val item = backStackEntry.toRoute<Items>()
        AppScaffold(
            currentScreenTitle = appViewModel.getCategoryName(item.categoryId),
            onNavigateUp = { navController.popBackStack() }
        ) { mod, _ ->
            ItemsScreen(
                modifier = mod,
                categoryId = item.categoryId,
                onClick = {
                    navController.navigate(SellerInventory(it))
                },
                onSearchRequest = {
                    navController.navigate(SellerInventory(MongoDB.searchForItem(it)._id))
                }
            )
        }
    }
}

fun NavGraphBuilder.sellerInventory(navController: NavHostController) {
    composable<SellerInventory>(
        typeMap = mapOf(
            typeOf<ObjectId>() to CustomNavType.objectIdType
        )
    ) { backStackEntry ->
        val item = backStackEntry.toRoute<SellerInventory>()
        val appViewModel: AppViewModel = viewModel()
        AppScaffold(
            currentScreenTitle = appViewModel.getItemName(item.itemId),
            onNavigateUp = { navController.navigateUp() }
        ) { modifier, _ ->
            SaleItemsScreen(modifier = modifier, itemId = item.itemId, onClick = { navController.navigate(SellerDetails(it))})
        }
    }
}

fun NavGraphBuilder.sellerDetails(
    activity: Activity,
    navController: NavHostController
) {
    composable<SellerDetails>(
        typeMap = mapOf(
            typeOf<ObjectId>() to CustomNavType.objectIdType
        )
    ) { backStackEntry ->
        val saleItem = backStackEntry.toRoute<SellerDetails>()
        AppScaffold(currentScreenTitle = "Seller Details", onNavigateUp = { navController.navigateUp() }) { mod, _ ->
            SellerDetailsScreen(
                saleItemId = saleItem.saleItemId,
                activity = activity,
                navigateToChat = { navController.navigate(Chat(it)) },
                modifier = mod
            )
        }
    }
}

fun NavGraphBuilder.chat(navController: NavHostController) {
    composable<Chat> { backStackEntry ->
        val seller = backStackEntry.toRoute<Chat>()
        AppScaffold(currentScreenTitle = "Chat", onNavigateUp = { navController.navigateUp() }) { mod, _ ->
            ChatScreen(mod)
        }
    }
}