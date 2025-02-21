package com.example.documentbank.Firebase.FIrebaseAuth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import com.example.documentbank.Firebase.FIrebaseAuth.ui.AuthScreen
import com.example.documentbank.ui.theme.DocumentBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentBankTheme {
                Surface {
                    Scaffold {
                        AuthScreen()
                    }

                }
            }

        }
    }
}