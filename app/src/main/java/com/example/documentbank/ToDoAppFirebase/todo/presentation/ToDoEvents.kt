package com.example.documentbank.ToDoAppFirebase.todo.presentation

import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI

sealed class ToDoEvents {

    data class SaveToDo(val toDoUI: ToDoUI) : ToDoEvents()
    data class UpdateToDo(val toDoUI: ToDoUI) : ToDoEvents()
    data class DeleteToDo(val id: String) : ToDoEvents()

}