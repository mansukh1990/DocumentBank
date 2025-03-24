package com.example.documentbank.ThreadYoutube.screens

import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.documentbank.ThreadYoutube.navigation.Routes
import com.example.documentbank.ThreadYoutube.viewmodel.AuthViewModel

@Composable
fun Login(
    navController: NavHostController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    val viewModel: AuthViewModel = viewModel()
    val firebaseUser by viewModel.firebaseUser.observeAsState()
    val error by viewModel.error.observeAsState()

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(error) {
        if (firebaseUser != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }
    error?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.Text(
            text = "Login", style = TextStyle(
                fontWeight = FontWeight.ExtraBold, fontSize = 24.sp
            )
        )
        Spacer(Modifier.height(50.dp))
        androidx.compose.material3.OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        androidx.compose.material3.OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Password")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ), singleLine = true, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        ElevatedButton(modifier = Modifier.fillMaxWidth(), onClick = {
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email = email, password = password, context)
            }

        }) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp), text = "Login", style = TextStyle(
                    fontWeight = FontWeight.ExtraBold, fontSize = 20.sp
                )
            )
        }
        Spacer(Modifier.height(20.dp))
        TextButton(modifier = Modifier.fillMaxWidth(), onClick = {
            navController.navigate(Routes.Register.routes) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = "New User? Create an Account",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }

    }
}
