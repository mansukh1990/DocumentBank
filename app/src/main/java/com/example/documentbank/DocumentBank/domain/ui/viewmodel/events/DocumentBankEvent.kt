package com.example.documentbank.DocumentBank.domain.ui.viewmodel.events

import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse

sealed class NoteEvents {
    data class AddNoteEvent(val data: DocumentBankListResponse) : NoteEvents()
    data class DeleteNoteEvent(val id: Int) : NoteEvents()
    data class UpdateNoteEvent(val id: Int, val note: DocumentBankListResponse) : NoteEvents()
    object ShowNotes : NoteEvents()

}

sealed class NoteUiEvents {
    data class Success(val data: DocumentBankListResponse) : NoteUiEvents()
    data class Failure(val msg: String) : NoteUiEvents()
    object Loading : NoteUiEvents()

}