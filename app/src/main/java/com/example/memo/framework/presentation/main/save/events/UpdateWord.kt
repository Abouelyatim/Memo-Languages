package com.example.memo.framework.presentation.main.save.events

import com.example.memo.framework.presentation.main.save.SaveViewModel

fun SaveViewModel.updateWord(word: String) {
    state.value?.let { state ->
        this.state.value = state.copy(word = word)
    }
}