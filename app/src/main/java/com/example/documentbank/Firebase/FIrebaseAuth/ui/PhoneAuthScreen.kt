package com.example.documentbank.Firebase.FIrebaseAuth.ui

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.documentbank.utils.CommonDialog
import com.example.documentbank.utils.OtpView
import com.example.documentbank.utils.Resource
import com.example.documentbank.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhoneAuthScreen(
    activity: Activity,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var mobile by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isProgressDialog by remember { mutableStateOf(false) }

    if (isProgressDialog) {
        CommonDialog()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Mobile Number")
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = mobile,
                onValueChange = {
                    mobile = it
                },
                label = {
                    Text(text = "+91")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch(Dispatchers.Main) {
                    viewModel.createUserWithPhone(
                        phone = mobile,
                        activity = activity
                    ).collect {
                        when (it) {
                            is Resource.DataError -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.message.toString())
                            }

                            is Resource.Empty -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.message.toString())
                            }

                            is Resource.Loading -> {
                                isProgressDialog = true
                            }

                            is Resource.Success -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.data!!)
                            }
                        }
                    }
                }
            }) {
                Text(text = "Submit")
            }
            Spacer(Modifier.height(20.dp))
            Text(text = "Enter OTP")
            Spacer(Modifier.height(20.dp))
            OtpView(otpText = otp) {
                otp = it
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch(Dispatchers.Main) {
                    viewModel.signInWithCredential(
                        otp = otp
                    ).collect {
                        when (it) {
                            is Resource.DataError -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.message.toString())
                            }

                            is Resource.Empty -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.message.toString())
                            }

                            is Resource.Loading -> {
                                isProgressDialog = true
                            }

                            is Resource.Success -> {
                                isProgressDialog = false
                                activity.showMessage(message = it.data!!)
                            }
                        }
                    }
                }
            }) {
                Text(text = "Verify")
            }


        }
    }

}