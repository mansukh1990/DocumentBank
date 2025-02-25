package com.example.documentbank.ToDoAppFirebase.todo.domain.repository

import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun saveToDo(toDoUI: ToDoUI)
    suspend fun getToDo(): Flow<List<ToDoUI>>
    suspend fun updateToDo(toDoUI: ToDoUI)
    suspend fun deleteToDo(id: String)


}