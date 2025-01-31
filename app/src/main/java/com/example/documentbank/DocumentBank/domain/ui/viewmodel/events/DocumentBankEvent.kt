package com.example.documentbank.DocumentBank.domain.ui.viewmodel.events

import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse

sealed class DocumentBankEvent {

    data class AddDocumentBank(val documentBank: DocumentBankListResponse) : DocumentBankEvent()
    object GetDocumentBank : DocumentBankEvent()
    data class DeleteDocumentBank(val id: Int) : DocumentBankEvent()
    data class UpdateDocumentBank(val id: Int, val documentBank: DocumentBankListResponse) :
        DocumentBankEvent()

}