package com.example.memo.business.domain.models

data class Notification(
    val pk: Int?,
    val word: String,
    val url: String,
    val dismiss: Int,
    val pkSearch: Int,
)
