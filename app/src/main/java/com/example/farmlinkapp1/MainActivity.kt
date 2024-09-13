package com.example.farmlinkapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.farmlinkapp1.ui.theme.FarmLinkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FarmLinkAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FarmLinkApp(this)
//                    AppScaffold(currentScreenTitle = "Add New Item", onNavigateUp = { /*TODO*/ }, canNavigateUp = false) { mod, _ ->
//                        ChatScreen(mod)
//                    }

                    //UserDetailsScreen(activity = this, navigateToUserType = { Log.d("fuk", "hello") })
                }
            }
        }
    }
}