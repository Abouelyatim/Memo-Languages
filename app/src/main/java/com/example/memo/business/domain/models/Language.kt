package com.example.memo.business.domain.models

data class Language (
    val pk: Int?,
    val name: String,
){
    fun isValid():Boolean{
        return name.isNotEmpty() && name.isNotBlank()
    }
}
