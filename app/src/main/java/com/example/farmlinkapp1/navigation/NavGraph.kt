package com.example.farmlinkapp1.navigation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmlinkapp1.common.UserTypeScreen
import com.example.farmlinkapp1.util.Constants.APP_ID
import io.realm.kotlin.mongodb.App

@Composable
fun NavGraphSetup(
    activity: Activity,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        startDestination = getStartDestination(),
        navController = navController,
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        authentication(activity, navController)
        userType(navController)
        buyerApp(activity, navController)
        sellerApp(navController)
    }
}

fun NavGraphBuilder.userType(navController: NavHostController) {
    composable<UserType> {
        UserTypeScreen(
            onContinueAsBuyer = { navController.navigate(BuyerApp) },
            onContinueAsSeller = { navController.navigate(SellerApp) }
        )
    }
}

private fun getStartDestination(): Any {
    val user = App.create(APP_ID).currentUser

    return if (user != null && user.loggedIn) UserType
    else Authentication
}