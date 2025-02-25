package com.example.documentbank.ToDoAppFirebase.todo.presentation

import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI

data class ToDoState(
    val isLoading: Boolean = true,
    val toDoList: List<ToDoUI> = emptyList(),
)