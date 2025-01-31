package com.example.documentbank.DocumentBank.data.model.login

data class LoginRequest(
    val email: String,
    val password: String,
    val app_version: String,
    val fcm_token: String
)
