package com.example.documentbank.Firebase.FIrebaseAuth.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.documentbank.Firebase.FIrebaseAuth.AuthUser
import com.example.documentbank.Firebase.FIrebaseAuth.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun createUser(
        authUser: AuthUser
    ) = authRepository.createUser(authUser)

    fun loginUser(
        authUser: AuthUser
    ) = authRepository.loginUser(authUser)

    fun createUserWithPhone(
        phone: String,
        activity: Activity
    ) = authRepository.createUserWithPhone(phone = phone, activity = activity)

    fun signInWithCredential(
        otp: String
    ) = authRepository.signWithCredential(otp = otp)
}