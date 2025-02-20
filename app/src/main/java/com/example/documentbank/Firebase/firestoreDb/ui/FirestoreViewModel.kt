package com.example.documentbank.Firebase.firestoreDb.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentbank.Firebase.firestoreDb.FirestoreModelResponse
import com.example.documentbank.Firebase.firestoreDb.repository.FirestoreRepository
import com.example.documentbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _res: MutableState<FirestoreState> = mutableStateOf(FirestoreState())
    val res: MutableState<FirestoreState> = _res

    private val _updateData: MutableState<FirestoreModelResponse> = mutableStateOf(
        FirestoreModelResponse(item = FirestoreModelResponse.FirestoreItem())
    )
    val updateData: State<FirestoreModelResponse> = _updateData

    fun setData(data: FirestoreModelResponse) {
        _updateData.value = data
    }

    fun insert(item: FirestoreModelResponse.FirestoreItem) = repository.insert(item)

    init {
        getItems()
    }

    fun getItems() = viewModelScope.launch {
        repository.getItems().collect {
            when (it) {
                is Resource.DataError -> {
                    _res.value = FirestoreState(
                        error = it.toString()
                    )
                }

                is Resource.Empty -> {
                    _res.value = FirestoreState(
                        data = emptyList()
                    )
                }

                is Resource.Loading -> {
                    _res.value = FirestoreState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _res.value = FirestoreState(
                        data = it.data!!
                    )
                }
            }
        }
    }

    fun delete(key: String) = repository.delete(key)

    fun update(item: FirestoreModelResponse) = repository.update(item)


}

data class FirestoreState(
    val data: List<FirestoreModelResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)