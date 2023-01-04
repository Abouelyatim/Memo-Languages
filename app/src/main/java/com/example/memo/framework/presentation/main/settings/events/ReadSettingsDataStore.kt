package com.example.memo.framework.presentation.main.settings.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.settings.SettingsEvents
import com.example.memo.framework.presentation.main.settings.SettingsViewModel
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.launch

fun SettingsViewModel.readSettingsDataStore(){
    state.value?.let { state ->
        var notificationNumber = 5
        var dismissNumber = 2
        viewModelScope.launch {
            appDataStoreSource.readValue(DataStoreKeys.NOTIFICATION_NUMBER)?.let {
                notificationNumber = it.toInt()
            }
            appDataStoreSource.readValue(DataStoreKeys.DISMISS_NUMBER)?.let {
                dismissNumber = it.toInt()
            }
            onTriggerEvent(SettingsEvents.UpdateNotificationNumberEvent(notificationNumber))
            onTriggerEvent(SettingsEvents.UpdateDismissNumberEvent(dismissNumber))
        }
    }
}