package com.example.documentbank.DocumentBank.data.model.login

data class LoginResponse(
    val city: String,
    val created_at: String,
    val customer_id: String,
    val dc_hub_id: String,
    val deleted: String,
    val deleted_at: String,
    val email: String,
    val email_verified_at: String,
    val fcm_token: String,
    val id: String,
    val image: String,
    val iocl_account_id: String,
    val is_display_all: String,
    val is_user_active_api_today: Int,
    val kc_token: String,
    val last_login: String,
    val mobile_num: String,
    val name: String,
    val otp: String,
    val parent_id: String,
    val role: String,
    val session_id: String,
    val status: String,
    val token: String,
    val updated_at: String,
    val username: String
)