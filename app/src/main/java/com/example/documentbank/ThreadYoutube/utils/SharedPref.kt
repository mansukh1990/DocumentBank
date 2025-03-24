package com.example.documentbank.ThreadYoutube.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref {

    fun storeData(
        name: String,
        email: String,
        bio: String,
        userName: String,
        imageUrl: String,
        context: Context
    ) {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("bio", bio)
        editor.putString("userName", userName)
        editor.putString("imageUrl", imageUrl)
        editor.apply()

    }

    fun getUserName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("userName", null)

    }

    fun getName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("name", null)

    }

    fun getBio(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("bio", null)

    }

    fun getEmail(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("email", null)

    }

    fun getImageUrl(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("users", MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl", null)

    }
}