package com.example.documentbank.ToDoAppFirebase.todo.data.mapper

import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import java.util.UUID

fun ToDoUI.toToDoDTO(): ToDoDTO {
    return ToDoDTO(
        id = id ?: UUID.randomUUID().toString(),
        title = title!!,
        description = description,
        priority = priority,
        dateAdded = System.currentTimeMillis()
    )
}