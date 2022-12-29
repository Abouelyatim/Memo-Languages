package com.example.memo.framework.presentation.main.save.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.SuccessHandling
import com.example.memo.framework.presentation.main.save.SaveEvents
import com.example.memo.framework.presentation.main.save.SaveViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun SaveViewModel.deleteNotification(pk: Int){
    baseState.value?.let { baseState ->
        deleteNotificationFromCacheUseCase.deleteNotificationFromCache(
            pk = pk
        ).onEach { dataState ->
            this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

            dataState.data?.let { response ->
                if(response.message == SuccessHandling.SUCCESS_DELETED){
                    onTriggerEvent(SaveEvents.GetNotificationsEvent)
                }else{
                    appendToMessageQueue(
                        stateMessage = StateMessage(
                            response = response
                        )
                    )
                }
            }

            dataState.stateMessage?.let { stateMessage ->
                appendToMessageQueue(stateMessage)
            }
        }.launchIn(viewModelScope)
    }
}