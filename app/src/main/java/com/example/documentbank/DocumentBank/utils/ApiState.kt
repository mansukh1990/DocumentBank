package com.example.documentbank.DocumentBank.utils

sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Failure(val message: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}