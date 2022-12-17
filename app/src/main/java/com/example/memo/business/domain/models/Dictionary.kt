package com.example.memo.business.domain.models

data class Dictionary (
    val pk: Int?,
    val name: String,
    val url: String,
){
    fun isValid():Boolean{
        return name.isNotEmpty() && name.isNotBlank() && url.isNotEmpty() && url.isNotBlank()
    }
}