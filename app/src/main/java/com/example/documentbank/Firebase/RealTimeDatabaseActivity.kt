package com.example.documentbank.Firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.documentbank.ui.theme.DocumentBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealTimeDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentBankTheme {

            }

        }
    }
}