package com.example.documentbank.DocumentBank.domain.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentbank.BaseApplication
import com.example.documentbank.DocumentBank.data.model.ApiResp
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.UploadDocumentBankMediaFileRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.DocumentBank.domain.repository.MainRepository
import com.example.documentbank.DocumentBank.domain.use_case.UploadDocumentBankMediaFileUseCase
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.R
import com.example.documentbank.common.ResponseCode.checkNetworkAvailability
import com.example.documentbank.remote.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DocumentBankListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val uploadDocumentBankMediaFileUseCase: UploadDocumentBankMediaFileUseCase
) : ViewModel() {

    var leadCode = ""
    var modelType = ""
    var modelId = ""

    private val _loginState = MutableStateFlow<ApiState<LoginResponse>?>(null)
    val loginState: StateFlow<ApiState<LoginResponse>?> = _loginState.asStateFlow()

    private val _documentTypes =
        MutableStateFlow<ApiState<List<DocumentTypeResponse>>>(ApiState.Loading)
    val documentTypes =
        _documentTypes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedDocumentType = MutableStateFlow<DocumentTypeResponse?>(null)
    val selectedDocumentType = _selectedDocumentType.asStateFlow()

    private val _documents =
        MutableStateFlow<ApiState<List<DocumentBankListResponse>>>(ApiState.Loading)
    val documents: StateFlow<ApiState<List<DocumentBankListResponse>>> = _documents

    private val _documentList = MutableStateFlow<List<DocumentBankListResponse>>(emptyList())
    val documentList: StateFlow<List<DocumentBankListResponse>> = _documentList


    private val _documentsTypeValue =
        MutableStateFlow<ApiState<List<GetDocumentTypeValueResponse>>>(ApiState.Loading)
    val documentsTypeValue: StateFlow<ApiState<List<GetDocumentTypeValueResponse>>> =
        _documentsTypeValue.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _uploadState = MutableStateFlow<ApiState<Any?>>(ApiState.Loading)
    val uploadState: StateFlow<ApiState<Any?>> = _uploadState

    init {
        fetchDocumentTypes()
    }


    fun getDocumentTypeValue(request: GetDocumentTypeValueRequest) {
        viewModelScope.launch {
            _documentsTypeValue.value = ApiState.Loading
            try {
                repository.getDocumentTypeValue(request).collect {
                    _documentsTypeValue.value = it
                }

            } catch (e: Exception) {
                _documentsTypeValue.value =
                    ApiState.Failure(e.localizedMessage ?: "Unknown Error")
            }
        }

    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                // Login and save token
                repository.login(loginRequest).collect {
                    _loginState.value = it
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isLoggedIn() {
        viewModelScope.launch {
            repository.isLoggedIn()
        }
    }


    private val request = DocumentTypeRequest(
        model = "Load",
        modelId = "2016",
        linkedInputTypeId = "1",
        documentBankId = "1"
    )

  fun fetchDocumentTypes(
    ) {
        viewModelScope.launch {
            repository.getDocumentTypes(
                request = request
            ).collect {
                _documentTypes.value = it
            }
        }
    }
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }


    fun setSelectedDocumentType(documentType: DocumentTypeResponse) {
        _selectedDocumentType.value = documentType
    }

    fun fetchDocuments(
        request: DocumentBankListRequest,
    ) {
        viewModelScope.launch {
            val allDocuments = mutableListOf<DocumentBankListResponse>()
            var currentPage = 1
            var lastPage: Int? = null

            do {
                repository.getDocumentBankList(request, currentPage)
                    .onStart {
                        _documents.value = ApiState.Loading
                    }
                    .catch { e ->
                        _documents.value = ApiState.Failure(e.message ?: "unknown error")
                    }.collect { result ->
                        _documents.value = ApiState.Success(result)
                        allDocuments.addAll(result)
                        lastPage = result.size
                        currentPage++
                    }
            } while (lastPage != null && currentPage <= lastPage!!)
            _documents.value = ApiState.Success(allDocuments)

        }
    }

    fun uploadImage(
        file: File,
    ) {
        viewModelScope.launch {
            _uploadState.value = ApiState.Loading
            repository.uploadDocument(
                file
            ).collect {
                _uploadState.value = ApiState.Success(it.toString())
                fetchDocuments(
                    request = DocumentBankListRequest(
                        modelId = "2016",
                        fileType = "image",
                        model = "Load",
                        collection = "document-bank"
                    )
                )
            }

        }
    }

    private val _imageList = MutableStateFlow<List<Uri>>(emptyList())
    val imageList: StateFlow<List<Uri>> = _imageList.asStateFlow()

    fun addImage(uri: Uri) {
        _imageList.value = listOf(uri) + _imageList.value

    }

    fun removeImage(uri: Uri) {
        _imageList.value -= uri
    }

    fun updateDocument(updatedDocument: DocumentBankListResponse) {
        _documentList.value = _documentList.value.map {
            if (it.id == updatedDocument.id) updatedDocument else it
        }
    }

    fun deleteDocument(documentId: String) {
        _documentList.value = _documentList.value.filterNot { it.id == documentId }
    }

    private val uploadDocumentBankMediaFilePrivate: MutableStateFlow<Resource<ApiResp<Any?>>> =
        MutableStateFlow(Resource.Empty())
    val uploadDocumentBankMediaFileLiveData: MutableStateFlow<Resource<ApiResp<Any?>>> get() = uploadDocumentBankMediaFilePrivate

    fun uploadDocumentBankMediaFile(apiRequest: UploadDocumentBankMediaFileRequest) {
        viewModelScope.launch {
            if (checkNetworkAvailability()) {
                uploadDocumentBankMediaFileUseCase.execute(request = apiRequest).collect {
                    uploadDocumentBankMediaFilePrivate.value = it
                }
            } else {
                uploadDocumentBankMediaFilePrivate.value = Resource.DataError(
                    message = BaseApplication.applicationContext().getString(R.string.network_error)
                )
            }
        }
    }


}