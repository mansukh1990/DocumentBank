package com.example.documentbank.DocumentBank.data.model.documentbanklist

data class DocumentTypeRequest(
    val model: String,
    val modelId: String,
    val linkedInputTypeId: String,
    val documentBankId: String
)