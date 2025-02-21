package com.example.documentbank.Firebase.FIrebaseAuth.repository

import com.example.documentbank.Firebase.FIrebaseAuth.AuthUser
import com.example.documentbank.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createUser(
        authUser: AuthUser
    ): Flow<Resource<String>>

    fun loginUser(
        authUser: AuthUser
    ): Flow<Resource<String>>
}