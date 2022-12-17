package com.example.memo.framework.presentation.main.save.events

import com.example.memo.framework.presentation.main.save.SaveViewModel

fun SaveViewModel.onSavingCompleted(completed: Boolean){
    state.value?.let { state ->
        this.state.value = state.copy(isSavingCompleted = completed)
    }
}