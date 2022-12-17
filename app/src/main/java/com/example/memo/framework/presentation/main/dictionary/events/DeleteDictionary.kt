package com.example.memo.framework.presentation.main.dictionary.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.SuccessHandling
import com.example.memo.framework.presentation.main.dictionary.DictionaryEvents
import com.example.memo.framework.presentation.main.dictionary.DictionaryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun DictionaryViewModel.deleteDictionary(pk: Int){
    baseState.value?.let { baseState ->
        deleteDictionaryFromCacheUseCase.deleteDictionaryFromCache(
            pk = pk
        ).onEach { dataState ->
            this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

            dataState.data?.let { response ->
                if(response.message == SuccessHandling.SUCCESS_DELETED){
                    onTriggerEvent(DictionaryEvents.GetDictionariesEvent)
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