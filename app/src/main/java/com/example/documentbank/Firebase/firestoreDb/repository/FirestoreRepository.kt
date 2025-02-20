package com.example.documentbank.Firebase.firestoreDb.repository

import com.example.documentbank.Firebase.firestoreDb.FirestoreModelResponse
import com.example.documentbank.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    fun insert(
        item: FirestoreModelResponse.FirestoreItem
    ): Flow<Resource<String>>

    fun getItems(): Flow<Resource<List<FirestoreModelResponse>>>

    fun delete(key: String): Flow<Resource<String>>

    fun update(
        item: FirestoreModelResponse
    ): Flow<Resource<String>>
}