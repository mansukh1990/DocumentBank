package com.example.documentbank.DocumentBank.data.network

import com.example.documentbank.DocumentBank.data.model.ApiResp
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.common.ApiConstants
import com.example.documentbank.common.ApiParams
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query
import java.io.File

interface ApiService {

    @POST(ApiConstants.LOGIN)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): ApiResp<LoginResponse>

    @POST(ApiConstants.DOCUMENT_BANK_LIST)
    suspend fun getDocumentBankList(
        @Header("Authorization") token: String,
        @Query("model_id") modelId: String,
        @Query("file_type") fileType: String,
        @Query("model") model: String,
        @Query("page") page: Int,
        @Query("collection") collection: String,
    ): ApiResp<List<DocumentBankListResponse>>

    @POST(ApiConstants.GET_DOCUMENT_TYPE)
    suspend fun getDocumentTypes(
        @Header("Authorization") token: String,
        @Query("model") model: String,
        @Query("model_id") modelId: String,
        @Query("linked_input_type_id") linkedInputTypeId: String,
        @Query("document_bank_id") documentBankId: String
    ): ApiResp<List<DocumentTypeResponse>>


    @POST(ApiConstants.UPLOAD_DOCUMENT_BANK_MEDIA_FILE)
    suspend fun uploadDocumentBankMediaFile(
        @Header("Authorization") token: String,
        @Query("model") model: String,
        @Query("model_id") modelId: String,
        @Query("collection") collection: String,
        @Query("media_file") media_file: File
    ): ApiResp<Unit>

    @POST(ApiConstants.GET_DOCUMENT_TYPE_VALUE)
    suspend fun getDocumentTypeValue(
        @Header("Authorization") token: String,
        @Query("linked_input_type_id") linkedInputTypeId: String,
        @Query("document_bank_id") documentBankId: String
    ): ApiResp<List<GetDocumentTypeValueResponse>>

    @Multipart
    @POST(ApiConstants.UPLOAD_DOCUMENT_BANK_MEDIA_FILE)
    suspend fun uploadDocument(
        @Header("Authorization") token: String,
        @Part mediaFile: MultipartBody.Part,
        @Part("model") model: RequestBody,
        @Part("model_id") modelId: RequestBody,
        @Part("collection") collection: RequestBody
    ): ApiResp<Any?>

    @Multipart
    @POST(ApiConstants.UPLOAD_DOCUMENT_BANK_MEDIA_FILE)
    suspend fun uploadDocumentBankMediaFile(
        @Header(ApiParams.AUTHORIZATION) token: String?,
        @PartMap apiRequestMap: Map<String, String>,
        @Part mediaFile: MultipartBody.Part?,
    ): ApiResp<Any?>

}