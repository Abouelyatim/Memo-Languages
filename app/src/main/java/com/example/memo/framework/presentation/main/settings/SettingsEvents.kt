package com.example.memo.framework.presentation.main.settings

import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.BaseEvents

sealed class SettingsEvents : BaseEvents() {

    object ReadSettingsDataStoreEvent: SettingsEvents()

    data class UpdateNotificationNumberEvent(val notificationNumber:Int): SettingsEvents()

    data class UpdateDismissNumberEvent(val dismissNumber:Int): SettingsEvents()

    data class Error(val stateMessage: StateMessage): SettingsEvents()

    object OnRemoveHeadFromQueue: SettingsEvents()
}