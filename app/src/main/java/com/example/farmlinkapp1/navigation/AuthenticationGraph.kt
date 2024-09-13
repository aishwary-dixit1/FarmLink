package com.example.farmlinkapp1.navigation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.farmlinkapp1.common.UserDetailsScreen
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.ui.auth.AuthScreen
import com.example.farmlinkapp1.ui.auth.AuthViewModel
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

fun NavGraphBuilder.authentication(activity: Activity, navController: NavHostController) {

    navigation<Authentication>(startDestination = SignIn) {
        signIn(navController)
        userDetails(activity, navController)
    }
}

fun NavGraphBuilder.signIn(navController: NavHostController) {
    composable<SignIn> {
        val authViewModel: AuthViewModel = viewModel()
        val messageBarState = rememberMessageBarState()
        val oneTapSignInState = rememberOneTapSignInState()
        AuthScreen(
            loadingState = oneTapSignInState.opened,
            onButtonClick = {
                authViewModel.setLoading(true)
                oneTapSignInState.open()

            },
            oneTapState = oneTapSignInState,
            messageBarState = messageBarState,
            onTokenIdReceived = { tokenId ->
                authViewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        if (it) {
                            messageBarState.addSuccess("Successfully Authenticated")
                            if (MongoDB.userKnown()) Log.d("fuck", "user known")
                            else Log.d("fuck", "user new")
                            navController.navigate(
                                if (MongoDB.userKnown()) UserType else UserDetails
                            )
                        }
                    },
                    onError = { error ->
                        messageBarState.addError(error)
                    }
                )
                authViewModel.setLoading(false)
            },
            onDialogDismissed = {
                authViewModel.setLoading(false)
                messageBarState.addError(Exception(it))
            }
        )
    }
}

fun NavGraphBuilder.userDetails(activity: Activity, navController: NavHostController) {
    composable<UserDetails> {
        UserDetailsScreen(activity = activity, navigateToUserType = { navController.navigate(UserType) })
    }
}
