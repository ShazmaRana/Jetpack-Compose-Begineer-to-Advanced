package com.example.navigationapp

import kotlinx.serialization.Serializable

@Serializable
object AddRoute

@Serializable
data class ProfileRoute(
    val name: String,
    val email: String,
    val rollnum: String,
    val university: String,
    val skills: String,
    val experience: String,
    val imageUri: String
)