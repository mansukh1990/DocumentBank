package com.example.documentbank.DocumentBank.data.model

data class ApiRes<T>(
    val responseCode: Int,
    val response: String,
    val status: String,
    val data: T?,
    val paginate: Paginate
)
