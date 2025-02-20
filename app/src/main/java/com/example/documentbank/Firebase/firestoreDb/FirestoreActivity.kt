package com.example.documentbank.Firebase.firestoreDb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.documentbank.Firebase.firestoreDb.ui.FireStoreScreen
import com.example.documentbank.ui.theme.DocumentBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirestoreActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentBankTheme {
                val isInput = remember { mutableStateOf(false) }
                Surface {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    isInput.value = true
                                }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "add")
                            }
                        }
                    ) {
                        FireStoreScreen(isInput)

                    }
                }
            }
        }
    }
}