package com.example.documentbank.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

fun Context.showMessage(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()

}

@Composable
fun CommonDialog() {

    Dialog(
        onDismissRequest = {}
    ) {
        CircularProgressIndicator()
    }

}