package com.example.documentbank.DocumentBank.data.model.documentbanklist

data class DocumentTypeResponse(
    val extra_place_holder: String,
    val id: Int,
    val is_api_call: Boolean,
    val is_extra_read_only: Boolean,
    val is_read_only: Boolean,
    val name: String,
    val placeholder: String,
    val type: String
)