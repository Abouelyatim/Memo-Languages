package com.example.memo.framework.presentation.main.settings.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.settings.SettingsViewModel
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.launch

fun SettingsViewModel.updateNotificationNumber(notificationNumber:Int){
    state.value?.let { state ->
        this.state.value = state.copy(notificationNumber = notificationNumber)
    }
    viewModelScope.launch {
        appDataStoreSource.setValue(DataStoreKeys.NOTIFICATION_NUMBER, notificationNumber.toString())
    }
}