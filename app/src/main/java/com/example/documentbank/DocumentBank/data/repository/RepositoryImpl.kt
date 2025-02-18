package com.example.documentbank.DocumentBank.data.repository

import android.content.Context
import android.util.Log
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.DocumentBank.data.network.ApiService
import com.example.documentbank.DocumentBank.domain.repository.MainRepository
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.DocumentBank.utils.TokenManager
import com.example.documentbank.common.ResponseCode
import com.example.documentbank.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: TokenManager,
    private val context: Context

) : MainRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<ApiState<LoginResponse>> = flow {
        try {
            val response = apiService.login(
                loginRequest = loginRequest
            )
            sharedPreferences.saveToken(response.data!!.token)
            emit(ApiState.Success(response.data))

        } catch (e: Exception) {
            emit(ApiState.Failure(e.localizedMessage ?: "unknown error"))
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return sharedPreferences.isLoggedIn()
    }

    override suspend fun getDocumentBankList(
        request: DocumentBankListRequest,
        page: Int
    ): Flow<List<DocumentBankListResponse>> =
        flow {
            val token = sharedPreferences.getToken()
                ?: throw Exception("Token not found. Please login first.")

            val response = apiService.getDocumentBankList(
                token = "Bearer $token",
                modelId = request.modelId,
                fileType = request.fileType,
                model = request.model,
                collection = request.collection,
                page = page

            )
            emit(response.data!!)
            Log.e("TAG", "getDocumentBankList:${response.data} ")

        }.flowOn(Dispatchers.IO)


    override suspend fun getDocumentTypes(
        request: DocumentTypeRequest
    ): Flow<ApiState<List<DocumentTypeResponse>>> = flow {
        emit(ApiState.Loading)
        try {
            val token = sharedPreferences.getToken()
                ?: throw Exception("Token not found. Please login first.")
            val response = apiService.getDocumentTypes(
                token = "Bearer $token",
                model = request.model,
                modelId = request.modelId,
                linkedInputTypeId = request.linkedInputTypeId,
                documentBankId = request.documentBankId
            )
            if (response.responseCode == ResponseCode.SUCCESS_CODE) {
                emit(ApiState.Success(response.data!!))
            } else {
                emit(ApiState.Failure(response.response))
            }


        } catch (e: Exception) {
            emit(ApiState.Failure(e.localizedMessage ?: "unknown error"))
        }

    }


    override suspend fun uploadDocumentBankMediaFile(
        model: String,
        modelId: String,
        collection: String,
        mediaFile: File
    ): Flow<ApiState<Unit>> = flow {
        emit(ApiState.Loading)
        try {
            val token = sharedPreferences.getToken()
                ?: throw Exception("Token not found. Please login first.")
            val response = apiService.uploadDocumentBankMediaFile(
                token = "Bearer $token",
                model = model,
                modelId = modelId,
                collection = collection,
                media_file = mediaFile
            )
            Log.e("TAG", "uploadDocumentBankMediaFile:${mediaFile} ")
            if (response.responseCode == 200) {
                emit(ApiState.Success(response.data!!))
            } else {
                emit(ApiState.Failure(response.response))
            }


        } catch (e: Exception) {
            emit(ApiState.Failure(e.localizedMessage ?: "Unknown Error"))
        }
    }

    override suspend fun uploadDocument(mediaFile: File): Flow<Resource<Any?>> {

        val requestBody = "mediaFile".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody =
            MultipartBody.Part.createFormData("media_file", mediaFile.name, requestBody)

        return flow {
            try {
                emit(Resource.Loading())
                val response = apiService.uploadDocument(
                    token = "Bearer ${sharedPreferences.getToken()}",
                    mediaFile = multipartBody,
                    model = "Load".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                    modelId = "2004".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                    collection = "document-bank".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
                emit(Resource.Success(data = response))
                Log.e("TAG", "${response} ")

            } catch (e: IOException) {
                emit(Resource.DataError(message = "Couldn't upload image."))
            } catch (e: HttpException) {
                emit(Resource.DataError(message = e.message()))
            }
        }
    }


    override suspend fun getDocumentTypeValue(request: GetDocumentTypeValueRequest):
            Flow<ApiState<List<GetDocumentTypeValueResponse>>> = flow {
        emit(ApiState.Loading)
        try {
            val token = sharedPreferences.getToken()
                ?: throw Exception("Token not found. Please login first.")
            val response = apiService.getDocumentTypeValue(
                token = "Bearer $token",
                linkedInputTypeId = request.linked_input_type_id,
                documentBankId = request.document_bank_id
            )
            if (response.responseCode == ResponseCode.SUCCESS_CODE) {
                emit(ApiState.Success(response.data!!))
            } else {
                emit(ApiState.Failure(response.response))
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}
