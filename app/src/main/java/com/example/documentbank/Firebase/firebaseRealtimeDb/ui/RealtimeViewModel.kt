package com.example.documentbank.Firebase.firebaseRealtimeDb.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentbank.Firebase.firebaseRealtimeDb.RealtimeModelResponse
import com.example.documentbank.Firebase.firebaseRealtimeDb.repository.RealtimeRepository
import com.example.documentbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val repository: RealtimeRepository
) : ViewModel() {

    private val _res: MutableState<ItemState> = mutableStateOf(ItemState())
    val res: State<ItemState> = _res

    fun insert(items: RealtimeModelResponse.RealtimeItems) = repository.insert(items)

    private val _updateRes:MutableState<RealtimeModelResponse> = mutableStateOf(
        RealtimeModelResponse(
            items = RealtimeModelResponse.RealtimeItems(),
        )
    )
    val updateRes:State<RealtimeModelResponse> = _updateRes

    fun setData(data:RealtimeModelResponse){
        _updateRes.value = data
    }

    init {
        viewModelScope.launch {
            repository.getItems().collect {
                when (it) {
                    is Resource.Success -> {
                        _res.value = ItemState(
                            item = it.data ?: emptyList()
                        )

                    }

                    is Resource.DataError -> {
                        _res.value = ItemState(
                            error = it.message.toString()
                        )

                    }

                    is Resource.Loading -> {
                        _res.value = ItemState(
                            isLoading = true
                        )

                    }

                    is Resource.Empty -> {
                        _res.value = ItemState(
                            item = emptyList()
                        )

                    }
                }

            }
        }
    }

    fun delete(key: String) = repository.delete(key)

    fun update(items: RealtimeModelResponse) = repository.update(items)

}

data class ItemState(
    val item: List<RealtimeModelResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false

)