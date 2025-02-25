package com.example.documentbank.ToDoAppFirebase

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.example.documentbank.R
import com.example.documentbank.ToDoAppFirebase.todo.presentation.ToDoScreen
import com.example.documentbank.ToDoAppFirebase.todo.presentation.ToDoViewModel
import com.example.documentbank.ui.theme.DocumentBankTheme

class ToDoAppFirebaseActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color(0xff03045e).toArgb()
            )
        )
        setContent {
            DocumentBankTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        androidx.compose.material3.TopAppBar(
                            title = {
                                Text(
                                    text = "ToDo App",
                                    color = Color.White
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(R.color.dark_blue)
                            )
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val viewModel: ToDoViewModel by viewModels()
                        val state by viewModel.state.collectAsState()

                        ToDoScreen(
                            state = state,
                            events = viewModel::onEvent
                        )
                    }


                }

            }
        }
    }
}