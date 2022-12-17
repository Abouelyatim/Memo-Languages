package com.example.memo.business.domain.models

import com.example.memo.business.domain.utils.isNotEmptyAndNotBlank

data class Search (
    val pk: Int?,
    val word: String,
    val languageSource: String,
    val languageDestination: String,
    val url: String
){
    fun isValid():Boolean{
        return word.isNotEmptyAndNotBlank() && languageSource.isNotEmptyAndNotBlank() &&
                languageDestination.isNotEmptyAndNotBlank() && url.isNotEmptyAndNotBlank()
    }
}