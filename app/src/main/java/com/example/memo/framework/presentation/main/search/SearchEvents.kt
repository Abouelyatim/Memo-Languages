package com.example.memo.framework.presentation.main.search

import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.BaseEvents

sealed class SearchEvents : BaseEvents() {

    data class SaveWordDataStoreEvent(
        val word: String
    ): SearchEvents()

    data class SearchWordEvent(
        val word: String
    ): SearchEvents()

    data class UpdateCurrentUrlEvent(
        val url: String
    ): SearchEvents()

    data class Error(val stateMessage: StateMessage): SearchEvents()

    object OnRemoveHeadFromQueue: SearchEvents()
}
