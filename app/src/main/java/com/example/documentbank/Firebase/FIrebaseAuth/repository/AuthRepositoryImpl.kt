package com.example.documentbank.Firebase.FIrebaseAuth.repository

import android.app.Activity
import android.util.Log
import com.example.documentbank.Firebase.FIrebaseAuth.AuthUser
import com.example.documentbank.utils.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    private lateinit var onVerificationCode: String

    override fun createUser(authUser: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        auth.createUserWithEmailAndPassword(
            authUser.email!!,
            authUser.password!!
        ).addOnCompleteListener {
            trySend(Resource.Success(data = "User Created Successfully"))
            Log.d("UID", "current user id is : ${auth.currentUser?.uid} ")
        }.addOnFailureListener {
            trySend(Resource.DataError(message = it.toString()))
        }
        awaitClose {
            close()
        }

    }

    override fun loginUser(authUser: AuthUser): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        auth.signInWithEmailAndPassword(
            authUser.email!!,
            authUser.password!!
        ).addOnSuccessListener {
            trySend(Resource.Success(data = "User Login Successfully"))
            Log.d("LOGIN", "loginUser: ${auth.currentUser?.uid}")
        }.addOnFailureListener {
            trySend(Resource.DataError(it.toString()))
        }
        awaitClose {
            close()
        }

    }

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<Resource<String>> =
        callbackFlow {
            trySend(Resource.Loading())

            val onVerificationCallback =
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        trySend(Resource.DataError(p0.toString()))
                    }

                    override fun onCodeSent(
                        verificationCode: String,
                        p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        super.onCodeSent(verificationCode, p1)
                        trySend(Resource.Success(data = "OTP Sent Successfully"))
                        onVerificationCode = verificationCode
                    }

                }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91$phone")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(onVerificationCallback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            Log.e("OTP", PhoneAuthProvider.verifyPhoneNumber(options).toString())

            awaitClose {
                close()
            }
        }

    override fun signWithCredential(otp: String): Flow<Resource<String>> = callbackFlow {
        trySend(Resource.Loading())
        val credential = PhoneAuthProvider.getCredential(onVerificationCode, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(data = "OTP Verified Successfully"))
                }
            }
            .addOnFailureListener {
                trySend(Resource.DataError(it.toString()))
                Log.e("OTP", "signWithCredential:${credential} ")
            }
        awaitClose {
            close()
        }

    }
}