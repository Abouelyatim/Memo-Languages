package com.example.memo.framework.presentation.main.dictionary.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.dictionary.DictionaryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun DictionaryViewModel.getDictionaries(){
    state.value?.let { state ->
        baseState.value?.let { baseState ->
            getDictionariesFromCacheUseCase.getDictionariesFromCache().onEach { dataState ->
                this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(dictionaryList = list)
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }
            }.launchIn(viewModelScope)
        }
    }
}