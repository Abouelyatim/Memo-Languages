package com.example.memo.framework.presentation.main.search.events

import com.example.memo.framework.presentation.main.search.SearchViewModel

fun SearchViewModel.updateCurrentUrl(url: String) {
    state.value?.let { state ->
        this.state.value = state.copy(url = url)
    }
}