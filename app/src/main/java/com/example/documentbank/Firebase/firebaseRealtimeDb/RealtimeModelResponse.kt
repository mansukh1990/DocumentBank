package com.example.documentbank.Firebase.firebaseRealtimeDb

data class RealtimeModelResponse(
    val items: RealtimeItems?,
    val key: String?
) {
    data class RealtimeItems(
        val title: String?,
        val description: String?,
    )
}
