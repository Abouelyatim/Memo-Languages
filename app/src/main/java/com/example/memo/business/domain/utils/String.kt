package com.example.memo.business.domain.utils

fun String.isNotEmptyAndNotBlank():Boolean{
    return this.isNotEmpty() && this.isNotBlank()
}