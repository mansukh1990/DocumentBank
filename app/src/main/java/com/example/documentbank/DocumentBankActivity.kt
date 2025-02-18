package com.example.documentbank

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.documentbank.DocumentBank.Navigation.DocumentBankNavigation
import com.example.documentbank.ui.theme.DocumentBankTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentBankActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           DocumentBankTheme  {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    DocumentBankNavigation()
                    Log.d("API_KEY", BuildConfig.API_KEY)

                }
            }
        }
    }
}