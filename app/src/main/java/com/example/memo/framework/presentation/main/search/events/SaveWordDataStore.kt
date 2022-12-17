package com.example.memo.framework.presentation.main.search.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.search.SearchViewModel
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.launch

fun SearchViewModel.saveWordDataStoreEvent(word: String) {
    viewModelScope.launch {
        appDataStoreSource.setValue(DataStoreKeys.SEARCH_WORD, word)
    }
}