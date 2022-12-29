package com.example.memo.framework.presentation.main.save

import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.BaseEvents

sealed class SaveEvents : BaseEvents() {

    data class OpenUrlOnBrowserEvent(
        val word: String,
        val url: String
    ): SaveEvents()

    data class UpdateSearchDismissEvent(
        val pkSearch: Int,
        val oldDismiss: Int
    ): SaveEvents()

    data class DeleteNotificationEvent(
        val pk: Int
    ): SaveEvents()

    object GetNotificationsEvent: SaveEvents()

    data class UpdateWordEvent(
        val word: String
    ): SaveEvents()

    data class UpdateUrlEvent(
        val url: String
    ): SaveEvents()

    object ReadUrlBundleEvent: SaveEvents()

    object ReadWordDataStoreEvent: SaveEvents()

    data class DeleteSearchEvent(
        val pk:Int,
    ): SaveEvents()

    data class InsertSearchEvent(
        val word:String,
        val url:String
    ): SaveEvents()

    object GetSearchesEvent: SaveEvents()

    data class OnSavingCompletedEvent(
        val completed: Boolean
    ): SaveEvents()

    data class UpdateLanguageSourceEvent(
        val language: String
    ): SaveEvents()

    data class UpdateLanguageDestinationEvent(
        val language: String
    ): SaveEvents()

    data class Error(val stateMessage: StateMessage): SaveEvents()

    object OnRemoveHeadFromQueue: SaveEvents()
}