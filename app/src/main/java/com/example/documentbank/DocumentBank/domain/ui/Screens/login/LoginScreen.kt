package com.example.documentbank.DocumentBank.domain.ui.Screens.login

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.documentbank.DocumentBank.Navigation.documentBankScreen
import com.example.documentbank.DocumentBank.data.model.login.LoginRequest
import com.example.documentbank.DocumentBank.domain.ui.viewmodel.DocumentBankListViewModel


@Composable
fun LoginScreenNew(
    navHostController: NavHostController,
    viewModel: DocumentBankListViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val loginState by viewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(16.dp)
                ) {
                    if (email.isEmpty()) {
                        Text("Email", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(16.dp)
                ) {
                    if (password.isEmpty()) {
                        Text("Password", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "please enter email and password", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.login(
                        loginRequest = LoginRequest(
                            email = "punit@celcius.in",
                            password = "123456",
                            app_version = "6",
                            fcm_token = "123456"
                        )
                    )
                    navHostController.navigate(documentBankScreen)
                }


            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
    LaunchedEffect(
        viewModel.loginState.collectAsState().value
    ) {
        viewModel.isLoggedIn()
        navHostController.navigate(documentBankScreen)

    }
}

