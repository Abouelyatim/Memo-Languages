package com.example.memo.framework.presentation.main.language.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.language.LanguageViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun LanguageViewModel.getLanguages(){
    baseState.value?.let { baseState ->
        state.value?.let { state ->
            getLanguagesFromCacheUseCase.getLanguagesFromCache().onEach { dataState ->
                this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(languageList = list)
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }
            }.launchIn(viewModelScope)
        }
    }
}