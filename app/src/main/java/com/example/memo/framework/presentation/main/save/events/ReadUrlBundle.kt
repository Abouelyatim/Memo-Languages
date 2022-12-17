package com.example.memo.framework.presentation.main.save.events

import com.example.memo.framework.presentation.main.save.SaveViewModel

fun SaveViewModel.readUrlBundle() {
    state.value?.let { state ->
        val url = savedStateHandle.get<String>("SEARCH_URL")
        if(url != null){
            this.state.value = state.copy(closeActivity = true, url = url)
        }
    }
}