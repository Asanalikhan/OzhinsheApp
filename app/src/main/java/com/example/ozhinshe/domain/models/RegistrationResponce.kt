package com.example.ozhinshe.domain.models

data class RegistrationResponce(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)