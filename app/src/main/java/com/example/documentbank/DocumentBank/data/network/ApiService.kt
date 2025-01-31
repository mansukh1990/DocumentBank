package com.example.documentbank.DocumentBank.data.network

import com.example.documentbank.DocumentBank.data.model.ApiRes
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.common.ApiConstants
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.File

interface ApiService {

    @POST(ApiConstants.LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): ApiRes<LoginResponse>

    @POST(ApiConstants.DOCUMENT_BANK_LIST)
    suspend fun getDocumentBankList(
        @Header("Authorization") token: String,
        @Query("model_id") modelId: String,
        @Query("file_type") fileType: String,
        @Query("model") model: String,
        @Query("page") page: Int,
        @Query("collection") collection: String,
    ): ApiRes<List<DocumentBankListResponse>>

    @POST(ApiConstants.GET_DOCUMENT_TYPE)
    suspend fun getDocumentTypes(
        @Header("Authorization") token: String,
        @Query("model") model: String,
        @Query("model_id") modelId: String,
        @Query("linked_input_type_id") linkedInputTypeId: String,
        @Query("document_bank_id") documentBankId: String
    ): ApiRes<List<DocumentTypeResponse>>


    @POST(ApiConstants.UPLOAD_DOCUMENT_BANK_MEDIA_FILE)
    suspend fun uploadDocumentBankMediaFile(
        @Header("Authorization") token: String,
        @Query("model") model: String,
        @Query("model_id") modelId: String,
        @Query("collection") collection: String,
        @Query("media_file") media_file: File
    ): ApiRes<Unit>

    @POST(ApiConstants.GET_DOCUMENT_TYPE_VALUE)
    suspend fun getDocumentTypeValue(
        @Header("Authorization") token: String,
        @Query("linked_input_type_id") linkedInputTypeId: String,
        @Query("document_bank_id") documentBankId: String
    ): ApiRes<List<GetDocumentTypeValueResponse>>
}