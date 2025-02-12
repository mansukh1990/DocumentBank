package com.example.documentbank.DocumentBank.domain.use_case

import com.example.documentbank.DocumentBank.data.model.ApiResp
import com.example.documentbank.DocumentBank.data.model.documentbanklist.UploadDocumentBankMediaFileRequest
import com.example.documentbank.DocumentBank.data.network.ApiService
import com.example.documentbank.base.BaseUseCase
import com.example.documentbank.common.ResponseCode
import com.example.documentbank.remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadDocumentBankMediaFileUseCase @Inject constructor(
    private val apiServices: ApiService,
) : BaseUseCase<UploadDocumentBankMediaFileRequest, Any>() {
    override suspend fun execute(request: UploadDocumentBankMediaFileRequest): Flow<Resource<ApiResp<Any?>>> =
        flow {
            try {
                emit(Resource.Loading())
                val response =
                    apiServices.uploadDocumentBankMediaFile(
                        token = sharedPreferences.getToken(),
                        apiRequestMap = request.requestParams,
                        mediaFile = request.mediaFile
                    )
                if (response.responseCode != null && response.responseCode == ResponseCode.SUCCESS_CODE) {
                    emit(Resource.Success(data = response))
                } else {
                    if (response.options?.validation?.isNotEmpty() == true && response.responseCode == ResponseCode.ERROR_CODE) {
                        response.response = response.getAPIValidation()
                    }
                    response.leadCode = request.leadCode
                    emit(Resource.DataError(data = response, message = response.response))
                }
            } catch (e: Exception) {
                val message = getErrorMessage(e)
                emit(Resource.DataError(message = message))
            }
        }
}