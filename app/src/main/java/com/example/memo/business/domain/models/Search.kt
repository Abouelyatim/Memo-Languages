package com.example.memo.business.domain.models

import com.example.memo.business.domain.utils.isNotEmptyAndNotBlank

data class Search (
    val pk: Int?,
    val word: String,
    val languageSource: String,
    val languageDestination: String,
    val url: String,
    val dismiss: Int
){
    fun isValid():Boolean{
        return word.isNotEmptyAndNotBlank() && languageSource.isNotEmptyAndNotBlank() &&
                languageDestination.isNotEmptyAndNotBlank() && url.isNotEmptyAndNotBlank()
    }

    fun toNotification(): Notification {
        return Notification(
            pk = null,
            word = word,
            url = url,
            dismiss = dismiss,
            pkSearch = pk!!
        )
    }
}