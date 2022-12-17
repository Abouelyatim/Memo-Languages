package com.example.memo.framework.presentation.main.save.events

import com.example.memo.framework.presentation.main.save.SaveViewModel

fun SaveViewModel.updateLanguageDestination(language: String) {
    state.value?.let { state ->
        this.state.value = state.copy(languageDestination = language)
    }
}