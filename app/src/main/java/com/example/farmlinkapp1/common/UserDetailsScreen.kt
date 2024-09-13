package com.example.farmlinkapp1.common

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmlinkapp1.R
import com.example.farmlinkapp1.data.MongoDB
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@Composable
fun UserDetailsScreen(
    activity: Activity,
    navigateToUserType: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var address by remember {
        mutableStateOf("")
    }

    var phoneNo by remember {
        mutableStateOf("")
    }

    var enableButton by remember {
        mutableStateOf(false)
    }

    var askAddress by remember {
        mutableStateOf(false)
    }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(activity) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_pic),
            contentDescription = null
        )

        Text(
            text = "Please fill in these details to continue",
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = {
                Text(
                    text = "Your Address",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            maxLines = 1
        )

        OutlinedTextField(
            value = phoneNo,
            onValueChange = { phoneNo = it },
            label = {
                Text(
                    text = "Phone Number",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Button(onClick = {
            askAddress = true
            enableButton = true
        }) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.search_location),
                    contentDescription = "search location"
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text("Provide Location Access")
            }
        }

        Button(
            onClick = {
                scope.launch {
                    MongoDB.createUser(address, phoneNo)
                }
                navigateToUserType()
            },
            enabled = enableButton
        ) {
            Text(text = "Continue")
        }
    }

    if (askAddress) {
        GetAndStoreLocation(LocalContext.current, fusedLocationClient)
        MongoDB.haveNewLocation = true
    }
}
