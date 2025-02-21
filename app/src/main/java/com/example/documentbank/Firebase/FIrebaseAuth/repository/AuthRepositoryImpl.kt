package com.example.documentbank.Firebase.FIrebaseAuth.repository

import android.util.Log
import com.example.documentbank.Firebase.FIrebaseAuth.AuthUser
import com.example.documentbank.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

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
}