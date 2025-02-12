package com.example.documentbank.DocumentBank.data.model.documentbanklist

import okhttp3.MultipartBody

data class UploadDocumentBankMediaFileRequest(
    var requestParams: LinkedHashMap<String, String> = LinkedHashMap(),
    var mediaFile: MultipartBody.Part? = null,
    var leadCode: String = ""
)
