package com.example.memo.framework.presentation.main.save.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.save.SaveViewModel
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.launch

fun SaveViewModel.readWordDataStore() {
    state.value?.let { state ->
        var word :String? = null
        viewModelScope.launch {
            word = appDataStoreSource.readValue(DataStoreKeys.SEARCH_WORD)
        }
        word?.let {
            onTriggerEvent(com.example.memo.framework.presentation.main.save.SaveEvents.UpdateWordEvent(it))
        }
    }
}