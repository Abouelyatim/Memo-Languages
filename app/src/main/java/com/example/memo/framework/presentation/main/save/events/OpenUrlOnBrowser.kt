package com.example.memo.framework.presentation.main.save.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.framework.presentation.main.save.SaveViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun SaveViewModel.openUrlOnBrowser(word: String, url:String) {
    baseState.value?.let { baseState ->
        state.value?.let { state ->
            searchWordUseCase.searchWord(word,url)
                .onEach { dataState ->
                    this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { response ->
                        appendToMessageQueue(
                            stateMessage = StateMessage(
                                response = response
                            )
                        )
                    }

                    dataState.stateMessage?.let { stateMessage ->
                        appendToMessageQueue(stateMessage)
                    }
                }.launchIn(viewModelScope)
        }
    }
}