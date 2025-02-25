package com.example.documentbank.ToDoAppFirebase.todo.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.documentbank.R
import com.example.documentbank.ToDoAppFirebase.todo.domain.model.ToDoUI
import com.example.documentbank.ToDoAppFirebase.todo.presentation.components.ToDoDialog
import com.example.documentbank.ToDoAppFirebase.todo.presentation.components.ToDoItem
import com.example.documentbank.ToDoAppFirebase.todo.utils.Priority
import kotlinx.coroutines.launch

@Composable
fun ToDoScreen(
    state: ToDoState,
    events: (ToDoEvents) -> Unit
) {

    val scope = rememberCoroutineScope()
    val showToDoDialog = rememberSaveable { mutableStateOf(false) }

    var isEditMode by rememberSaveable { mutableStateOf(false) }

    var selectedTitle by rememberSaveable { mutableStateOf("") }
    var selectedDescription by rememberSaveable { mutableStateOf("") }
    var selectedPriority by rememberSaveable { mutableStateOf(Priority.LOW) }
    var selectedId by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark_blue)),
        contentAlignment = Alignment.Center
    ) {

        if (state.isLoading) {
            androidx.compose.material3.CircularProgressIndicator()
        } else if (state.toDoList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.toDoList, key = {
                    it.id!!
                }) { currentToDoUiItem ->
                    ToDoItem(toDoUI = currentToDoUiItem) {
                        selectedId = currentToDoUiItem.id!!
                        selectedTitle = currentToDoUiItem.title!!
                        selectedDescription = currentToDoUiItem.description
                        selectedPriority = currentToDoUiItem.priority
                        showToDoDialog.value = true
                        isEditMode = true

                    }
                }
            }
        } else {
            Text(
                text = "No ToDo items found, Please add some ToDo!!",
                color = colorResource(R.color.light1_blue)
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .size(110.dp)
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                showToDoDialog.value = true
            },
            shape = RoundedCornerShape(percent = 20),
            containerColor = colorResource(R.color.medium_blue)
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                tint = Color.White
            )
        }
        if (showToDoDialog.value) {
            ToDoDialog(
                isEditMode = isEditMode,
                onDismiss = {
                    showToDoDialog.value = false
                },
                onAddToDo = { title, description, priority ->
                    scope.launch {
                        val toDoUI = ToDoUI(
                            title = title,
                            description = description,
                            priority = priority
                        )
                        events(ToDoEvents.SaveToDo(toDoUI))
                    }
                    showToDoDialog.value = false
                },
                onUpdatedToDo = { title, description, priority ->
                    scope.launch {
                        val toDoUi = ToDoUI(
                            id = selectedId,
                            title = title,
                            description = description,
                            priority = priority
                        )
                        events(ToDoEvents.UpdateToDo(toDoUi))
                        Log.d("UPDATE", toDoUi.toString())
                    }
                    showToDoDialog.value = false
                },
                onDeletedToDo = {
                    scope.launch {
                        events(ToDoEvents.DeleteToDo(selectedId))
                    }
                    showToDoDialog.value = false
                },
                existingTitle = selectedTitle,
                existingDescription = selectedDescription,
                existingPriority = selectedPriority
            )
        }
    }

}