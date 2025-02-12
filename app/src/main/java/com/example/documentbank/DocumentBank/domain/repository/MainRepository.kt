package com.example.documentbank.DocumentBank.domain.repository

import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.remote.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

interface MainRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<ApiState<LoginResponse>>

    suspend fun isLoggedIn(): Boolean

    suspend fun getDocumentTypes(
        request: DocumentTypeRequest
    ): Flow<ApiState<List<DocumentTypeResponse>>>


    suspend fun getDocumentBankList(
        request: DocumentBankListRequest,
        page: Int
    ): Flow<List<DocumentBankListResponse>>

    suspend fun uploadDocumentBankMediaFile(
        model: String,
        modelId: String,
        collection: String,
        mediaFile: File
    ): Flow<ApiState<Unit>>

    suspend fun uploadDocument(
        mediaFile: File,
    ): Flow<Resource<Any?>>

    suspend fun getDocumentTypeValue(
        request: GetDocumentTypeValueRequest,
    ): Flow<ApiState<List<GetDocumentTypeValueResponse>>>


}