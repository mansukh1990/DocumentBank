package com.example.documentbank.ToDoAppFirebase.todo.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SimpleAlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Yes")

            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                androidx.compose.material3.Text(text = "No")
            }
        },
        text = {
            Text(text = message)
        },
        title = {
            Text(
                text = title
            )
        }
    )

}