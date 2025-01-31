package com.example.documentbank.DocumentBank.domain.ui.viewmodel.states

import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse

data class DocumentBankState(
    val isLoading: Boolean = false,
    val documentBankList: List<DocumentBankListResponse> = emptyList(),
    val error: String? = null
)
