package com.example.documentbank.DocumentBank.domain.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.GetDocumentTypeValueResponse
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.data.model.login.LoginResponse
import com.example.documentbank.DocumentBank.domain.repository.MainRepository
import com.example.documentbank.DocumentBank.utils.ApiState
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
    private val repository: MainRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<ApiState<LoginResponse>?>(null)
    val loginState: StateFlow<ApiState<LoginResponse>?> = _loginState.asStateFlow()

    private val _documentTypes =
        MutableStateFlow<ApiState<List<DocumentTypeResponse>>>(ApiState.Loading)
    val documentTypes: StateFlow<ApiState<List<DocumentTypeResponse>>> =
        _documentTypes.asStateFlow()

    private val _selectedDocumentType = MutableStateFlow<DocumentTypeResponse?>(null)
    val selectedDocumentType = _selectedDocumentType.asStateFlow()

    private val _documents =
        MutableStateFlow<ApiState<List<DocumentBankListResponse>>>(ApiState.Loading)
    val documents: StateFlow<ApiState<List<DocumentBankListResponse>>> = _documents.asStateFlow()

    private val _documentsTypeValue =
        MutableStateFlow<ApiState<List<GetDocumentTypeValueResponse>>>(ApiState.Loading)
    val documentsTypeValue: StateFlow<ApiState<List<GetDocumentTypeValueResponse>>> =
        _documentsTypeValue.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private var currentPage = 1
    private var lastPage = false

    fun uploadDocumentBankMediaFile(
        model: String,
        model_id: String,
        collection: String,
        media_file: File
    ) {
        viewModelScope.launch {
            repository.uploadDocumentBankMediaFile(
                model = model,
                model_id = model_id,
                collection = collection,
                media_file = media_file
            )
        }
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


    val request = DocumentTypeRequest(
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

    fun setSelectedDocumentType(documentType: DocumentTypeResponse) {
        _selectedDocumentType.value = documentType
    }

    fun fetchDocuments(
        request: DocumentBankListRequest,
    ) {
        if (_loading.value || lastPage) return
        viewModelScope.launch {
            repository.getDocumentBankList(request, currentPage)
                .onStart { _documents.value = ApiState.Loading } // Emit loading state
                .catch { e ->
                    _documents.value = ApiState.Failure(e.message ?: "Unknown error")
                } // Handle error
                .collect { result ->
                    _documents.value = ApiState.Success(result)
                    lastPage = result.isEmpty()
                    _loading.value = false
                }
        }
    }

}