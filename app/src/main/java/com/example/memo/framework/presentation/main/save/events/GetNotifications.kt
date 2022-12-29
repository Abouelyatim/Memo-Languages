package com.example.memo.framework.presentation.main.save.events

import androidx.lifecycle.viewModelScope
import com.example.memo.framework.presentation.main.save.SaveViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun SaveViewModel.getNotifications(){
    baseState.value?.let { baseState ->
        state.value?.let { state ->
            getNotificationsFromCacheUseCase.getNotificationsFromCache().onEach { dataState ->
                this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(notifications = list)
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }
            }.launchIn(viewModelScope)
        }
    }
}