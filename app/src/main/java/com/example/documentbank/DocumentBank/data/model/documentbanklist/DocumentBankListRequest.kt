package com.example.documentbank.DocumentBank.data.model.documentbanklist

data class DocumentBankListRequest(
    val modelId: String,
    val fileType: String,
    val model: String,
    val collection: String
)
