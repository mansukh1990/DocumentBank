package com.example.documentbank.ToDoAppFirebase.todo.domain.model

import com.example.documentbank.ToDoAppFirebase.todo.utils.Priority

data class ToDoUI(
    val id: String? = null,
    val title: String? = null,
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val dateAdded: String? = null
)
