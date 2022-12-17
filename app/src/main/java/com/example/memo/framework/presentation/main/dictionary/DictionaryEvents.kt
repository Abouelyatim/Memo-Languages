package com.example.memo.framework.presentation.main.dictionary

import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.BaseEvents

sealed class DictionaryEvents : BaseEvents() {

    data class DeleteDictionaryEvent(
        val pk:Int,
    ): DictionaryEvents()

    data class InsertDictionaryEvent(
        val name:String,
        val url:String
    ): DictionaryEvents()

    object GetDictionariesEvent: DictionaryEvents()

    data class Error(val stateMessage: StateMessage): DictionaryEvents()

    object OnRemoveHeadFromQueue: DictionaryEvents()
}