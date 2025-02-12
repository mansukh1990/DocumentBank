package com.example.documentbank.base

import android.text.TextUtils
import com.example.documentbank.BaseApplication
import com.example.documentbank.DocumentBank.data.model.ApiResp
import com.example.documentbank.DocumentBank.utils.TokenManager
import com.example.documentbank.R
import com.example.documentbank.common.ResponseCode
import com.example.documentbank.remote.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import okio.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseUseCase<in Q, out T> {

    @Inject
    protected lateinit var sharedPreferences: TokenManager

    abstract suspend fun execute(request: Q): Flow<Resource<ApiResp<@UnsafeVariance T?>>>


    fun <T> Flow<T>.withRetry(
        retries: Int = 3,
        delayMillis: Long = 1000,
        onError: (Throwable) -> Boolean = { true }
    ): Flow<T> {
        return retry(retries.toLong()) { cause ->
            delay(delayMillis)
            onError(cause)

        }
    }


    fun getErrorMessage(throwable: Throwable): String? {
        when (throwable) {
            is SocketTimeoutException -> {
                return BaseApplication.applicationContext()
                    .getString(R.string.str_socket_error_message)
            }

            is ConnectException -> {
                return BaseApplication.applicationContext()
                    .getString(R.string.str_connection_error_message)
            }

            is UnknownHostException -> {
                return BaseApplication.applicationContext()
                    .getString(R.string.str_unknownhost_error_message)
            }

            is java.io.IOException -> {
                return BaseApplication.applicationContext().getString(R.string.str_io_error_message)
            }

            else -> {
                return throwable.localizedMessage?.toString()
                    ?: BaseApplication.applicationContext().getString(R.string.network_error)
            }
        }
    }

    fun checkInternetException(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException || throwable is UnknownHostException || throwable is ConnectException || throwable is IOException
    }

    fun <T> ApiResp<T>.getAPIValidation(): String {
        var errorMsg = ""
        if (responseCode == ResponseCode.ERROR_CODE) {
            if (this.options?.validation != null) {
                var loopCounter=0
                for (i in this.options.validation) {
                    loopCounter++
                    errorMsg = if (TextUtils.isEmpty(errorMsg)) {
                        StringBuilder().apply {
                            append(loopCounter)
                            append(". ")
                            append(i)
                        }.toString()
                    } else {
                        StringBuilder(errorMsg).apply {
                            append("\n\n")
                            append(loopCounter)
                            append(". ")
                            append(i)
                        }.toString()
                    }
                }
            }
        }
        return errorMsg
    }

    suspend fun <T> retryApiCall(
        maxRetries: Int = 3,
        retryDelayMillis: Long = 1000,
        block: suspend () -> T
    ): T {
        var retryCount = 0

        while (true) {
            try {
                return block()
            } catch (e: Throwable) {
                if (++retryCount >= maxRetries) {
                    throw e
                }
                delay(retryDelayMillis)
            }
        }
    }
}
