package com.example.ozhinshe.modiedata

data class AuthorizationRespoce(
    val accessToken: String,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)