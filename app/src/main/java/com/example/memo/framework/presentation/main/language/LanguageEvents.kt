package com.example.memo.framework.presentation.main.language

import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.BaseEvents

sealed class LanguageEvents : BaseEvents() {

    data class DeleteLanguageEvent(
        val pk:Int,
    ): LanguageEvents()

    data class InsertLanguageEvent(
        val name:String,
    ): LanguageEvents()

    object GetLanguagesEvent: LanguageEvents()

    data class Error(val stateMessage: StateMessage): LanguageEvents()

    object OnRemoveHeadFromQueue: LanguageEvents()
}