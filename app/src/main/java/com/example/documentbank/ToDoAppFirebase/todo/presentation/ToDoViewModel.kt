package com.example.documentbank.ToDoAppFirebase.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentbank.ToDoAppFirebase.todo.data.repository.ToDoRepositoryImpl
import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import com.example.documentbank.ToDoAppFirebase.todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToDoViewModel : ViewModel() {

    val toDoRepository: ToDoRepository = ToDoRepositoryImpl()

    private val _state = MutableStateFlow(ToDoState())
    val state = _state.asStateFlow()

    init {
        getToDo()
    }

    fun onEvent(events: ToDoEvents) {
        when (events) {
            is ToDoEvents.SaveToDo -> {
                savaToDo(events.toDoUI)
            }

            is ToDoEvents.DeleteToDo -> {
                deleteToDo(events.id)
            }

            is ToDoEvents.UpdateToDo -> {
                updateToDo(events.toDoUI)
            }
        }
    }

    private fun savaToDo(toDoUI: ToDoUI) {

        viewModelScope.launch {
            toDoRepository.saveToDo(toDoUI)
        }
    }

    private fun getToDo() {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            toDoRepository.getToDo().collect { toDoList ->
                _state.value = _state.value.copy(
                    toDoList = toDoList,
                    isLoading = false
                )
            }
        }
    }

    private fun updateToDo(toDoUI: ToDoUI) {
        viewModelScope.launch {
            toDoRepository.updateToDo(toDoUI)
        }
    }

    private fun deleteToDo(id: String) {
        viewModelScope.launch {
            toDoRepository.deleteToDo(id)
        }
    }
}