package com.example.farmlinkapp1.data

import kotlinx.serialization.Serializable

@Serializable
data class CustomUserData(
    val name: String,
    val picture: String,
    val email: String
)