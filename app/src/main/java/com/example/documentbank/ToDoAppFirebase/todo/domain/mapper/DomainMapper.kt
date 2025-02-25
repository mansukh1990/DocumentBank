package com.example.documentbank.ToDoAppFirebase.todo.domain.mapper

import com.example.documentbank.ToDoAppFirebase.todo.utils.formatDateTime
import com.example.documentbank.ToDoAppFirebase.todo.data.mapper.ToDoDTO
import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI

fun ToDoDTO.toToDoUI(): ToDoUI {
    return ToDoUI(
        id = id!!,
        title = title,
        description = description!!,
        priority = priority,
        dateAdded = formatDateTime(dateAdded!!)
    )
}