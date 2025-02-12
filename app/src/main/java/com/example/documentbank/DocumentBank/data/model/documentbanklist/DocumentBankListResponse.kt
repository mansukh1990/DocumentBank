package com.example.documentbank.DocumentBank.data.model.documentbanklist

data class DocumentBankListResponse(
    val file_extension: String,
    val file_url: String,
    val id: String,
    val linked_extra_value: String,
    val linked_extra_value_name: String,
    val linked_input_status: String,
    val linked_input_type_id: String,
    val linked_input_type_name: String,
    val linked_input_value: String,
    val linked_input_value_name: String,
    val model_id: String,
    val model_type: String,
)