package com.example.documentbank.DocumentBank.utils

import android.content.SharedPreferences
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_SESSION_ID = "session_id"
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun saveSessionId(sessionId: String) {
        sharedPreferences.edit().putString(KEY_SESSION_ID, sessionId).apply()
    }


    fun getToken(): String? =
        sharedPreferences.getString(KEY_TOKEN, null)

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    fun getSessionId(): String? = sharedPreferences.getString(KEY_SESSION_ID, null)

    fun clearAuth() {
        sharedPreferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_SESSION_ID)
            .apply()
    }
}