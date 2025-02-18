package com.example.documentbank.Firebase.firebaseRealtimeDb.repository

import com.example.documentbank.Firebase.firebaseRealtimeDb.RealtimeModelResponse
import com.example.documentbank.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RealtimeRepository {

    fun insert(
        item: RealtimeModelResponse.RealtimeItems,
    ): Flow<Resource<String>>


    fun getItems(): Flow<Resource<List<RealtimeModelResponse>>>

    fun delete(key: String): Flow<Resource<String>>

    fun update(res: RealtimeModelResponse): Flow<Resource<String>>
}