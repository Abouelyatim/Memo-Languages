package com.example.memo.framework.presentation.main.settings.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.settings.SettingsViewModel
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.launch

fun SettingsViewModel.updateDismissNumber(dismissNumber:Int){
    state.value?.let { state ->
        this.state.value = state.copy(dismissNumber = dismissNumber)
    }
    viewModelScope.launch {
        appDataStoreSource.setValue(DataStoreKeys.DISMISS_NUMBER, dismissNumber.toString())
    }
}