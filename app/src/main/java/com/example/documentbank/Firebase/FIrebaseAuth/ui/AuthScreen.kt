package com.example.documentbank.Firebase.FIrebaseAuth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.documentbank.Firebase.FIrebaseAuth.AuthUser
import com.example.documentbank.utils.CommonDialog
import com.example.documentbank.utils.Resource
import com.example.documentbank.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {

    var emailRegister by remember { mutableStateOf("") }
    var emailLogin by remember { mutableStateOf("") }
    var passwordRegister by remember { mutableStateOf("") }
    var passwordLogin by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isProgressBarVisible = remember { mutableStateOf(false) }

    if (isProgressBarVisible.value) {
        CommonDialog()
    }
    LazyColumn(
        modifier = Modifier.padding(20.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Register")
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = emailRegister,
                    onValueChange = {
                        emailRegister = it
                    },
                    placeholder = {
                        Text(text = "Enter Email")
                    },
                    label = {
                        Text(text = "Enter Email")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )

                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = passwordRegister,
                    onValueChange = {
                        passwordRegister = it
                    },
                    placeholder = {
                        Text(text = "Enter Password")
                    },
                    label = {
                        Text(text = "Enter Password")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(Modifier.height(10.dp))
                androidx.compose.material3.Button(onClick = {
                    if (emailRegister.isEmpty()) {
                        context.showMessage(message = "Please Enter Email")
                        return@Button
                    }
                    if (passwordRegister.isEmpty()) {
                        context.showMessage(message = "Please Enter Password")
                        return@Button
                    }
                    scope.launch(Dispatchers.Main) {
                        viewModel.createUser(
                            AuthUser(
                                email = emailRegister,
                                password = passwordRegister
                            )
                        ).collect {
                            when (it) {
                                is Resource.DataError -> {
                                    context.showMessage(message = it.message.toString())
                                    isProgressBarVisible.value = false
                                }

                                is Resource.Empty -> {
                                    context.showMessage(message = it.message.toString())
                                    isProgressBarVisible.value = false
                                }

                                is Resource.Loading -> {
                                    isProgressBarVisible.value = true
                                }

                                is Resource.Success -> {
                                    context.showMessage(message = it.data!!)
                                    isProgressBarVisible.value = false
                                    emailRegister = ""
                                    passwordRegister = ""
                                }
                            }
                        }
                    }
                }) {
                    Text(text = "Register")
                }

            }

        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login")
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = emailLogin,
                    onValueChange = {
                        emailLogin = it
                    },
                    placeholder = {
                        Text(text = "Enter Email")
                    },
                    label = {
                        Text(text = "Enter Email")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )

                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = passwordLogin,
                    onValueChange = {
                        passwordLogin = it
                    },
                    placeholder = {
                        Text(text = "Enter Password")
                    },
                    label = {
                        Text(text = "Enter Password")
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(Modifier.height(10.dp))
                androidx.compose.material3.Button(onClick = {
                    if (emailLogin.isEmpty()) {
                        context.showMessage(message = "Please Enter Email")
                        return@Button
                    }
                    if (passwordLogin.isEmpty()) {
                        context.showMessage(message = "Please Enter Password")
                        return@Button
                    }
                    scope.launch(Dispatchers.Main) {
                        viewModel.loginUser(
                            AuthUser(
                                email = emailLogin,
                                password = passwordLogin
                            )
                        ).collect {
                            when (it) {
                                is Resource.DataError -> {
                                    context.showMessage(message = it.message.toString())
                                    isProgressBarVisible.value = false
                                }

                                is Resource.Empty -> {
                                    context.showMessage(message = it.message.toString())
                                    isProgressBarVisible.value = false
                                }

                                is Resource.Loading -> {
                                    isProgressBarVisible.value = true
                                }

                                is Resource.Success -> {
                                    context.showMessage(message = it.data!!)
                                    isProgressBarVisible.value = false
                                    emailLogin = ""
                                    passwordLogin = ""
                                }
                            }
                        }
                    }
                }) {
                    Text(text = "Login")
                }

            }

        }
    }

}