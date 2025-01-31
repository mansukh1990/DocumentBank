package com.example.documentbank.DocumentBank.data.model.documentbanklist

import java.io.File

data class UploadDocumentBankMediaFileRequest(
    val model: String,
    val model_id: String,
    val collection: String,
    val media_file: File
)
