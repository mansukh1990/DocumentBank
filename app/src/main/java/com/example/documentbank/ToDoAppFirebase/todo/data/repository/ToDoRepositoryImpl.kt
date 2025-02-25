package com.example.documentbank.ToDoAppFirebase.todo.data.repository

import com.example.documentbank.ToDoAppFirebase.todo.data.mapper.ToDoDTO
import com.example.documentbank.ToDoAppFirebase.todo.data.mapper.toToDoDTO
import com.example.documentbank.ToDoAppFirebase.todo.domain.mapper.toToDoUI
import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import com.example.documentbank.ToDoAppFirebase.todo.domain.repository.ToDoRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ToDoRepositoryImpl : ToDoRepository {

    val firebase = FirebaseDatabase.getInstance()
    private val toDoRef = firebase.getReference("toDoItems")

    override suspend fun saveToDo(toDoUI: ToDoUI) {

        val toDoDTO = toDoUI.toToDoDTO()

        try {
            toDoRef.child(toDoDTO.id!!).setValue(toDoDTO).await()

        } catch (e: Exception) {

        }
    }

    override suspend fun getToDo(): Flow<List<ToDoUI>> = callbackFlow {

        val listener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val toDoUIItems = snapshot.children.mapNotNull {
                    it.getValue(ToDoDTO::class.java)
                }.map {
                    it.toToDoUI()
                }
                trySend(toDoUIItems)
            }

            override fun onCancelled(error: DatabaseError) {
                close()

            }

        }
        toDoRef.addValueEventListener(listener)
        awaitClose {
            toDoRef.removeEventListener(listener)
        }
    }

    override suspend fun updateToDo(toDoUI: ToDoUI) {
        val toDoDTO = toDoUI.toToDoDTO()
        try {
            toDoRef.child(toDoDTO.id!!).setValue(toDoDTO).await()
        } catch (e: Exception) {

        }
    }

    override suspend fun deleteToDo(id: String) {

        try {
            toDoRef.child(id).removeValue().await()

        } catch (e: Exception) {

        }
    }

}