package com.example.farmlinkapp1.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.farmlinkapp1.data.MongoDB.setUserLocation
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun GetAndStoreLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
) {
    var userLocation by remember { mutableStateOf<Location?>(null) }
    val scope = rememberCoroutineScope()

    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Fine location access granted
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        userLocation = location
                        //scope.launch {
                            //storeLocationData(userLocation!!)
                        setUserLocation(userLocation!!)
                        //}
                    }
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        userLocation = location
                        scope.launch {
                            //storeLocationData(userLocation!!)
                        }
                    }
            }
        }
    }

    // Check if location permissions are granted
    val hasFineLocationPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    LaunchedEffect(Unit) {
        if (hasFineLocationPermission) {
            // Permissions already granted, get location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    userLocation = location
                    //scope.launch {
                        // storeLocationData(userLocation!!)
                        setUserLocation(userLocation!!)
                    //}
                }
        } else {
            // Request location permissions
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}
