package com.example.documentbank

import android.app.Application
import android.content.Context
import com.example.documentbank.DocumentBank.utils.TokenManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    init {
        instance = this
    }

    @Inject
    protected lateinit var sharedPreferences: TokenManager

    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}

