package com.example.documentbank.ToDoAppFirebase.todo.data.mapper

import com.example.documentbank.ToDoAppFirebase.todo.utils.Priority

data class ToDoDTO(
    val id: String? = null,
    val title: String = "",
    val description: String? = "",
    val priority: Priority = Priority.LOW,
    val dateAdded: Long? = null
)


