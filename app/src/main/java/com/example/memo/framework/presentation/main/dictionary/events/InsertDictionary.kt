package com.example.memo.framework.presentation.main.dictionary.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.SuccessHandling
import com.example.memo.framework.presentation.main.dictionary.DictionaryEvents
import com.example.memo.framework.presentation.main.dictionary.DictionaryViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun DictionaryViewModel.insertDictionary(name: String, url: String){
    state.value?.let { state ->
        baseState.value?.let { baseState ->
            insertDictionaryToCacheUseCase.insertDictionaryToCache(
                dictionary = Dictionary(
                    pk= null,
                    name= name,
                    url = url
                )
            ).onEach { dataState ->
                this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                dataState.data?.let { response ->
                    if(response.message == SuccessHandling.SUCCESS_CREATED){
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
}