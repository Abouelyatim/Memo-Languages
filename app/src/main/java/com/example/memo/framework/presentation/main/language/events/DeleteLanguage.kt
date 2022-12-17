package com.example.memo.framework.presentation.main.language.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.SuccessHandling
import com.example.memo.framework.presentation.main.language.LanguageEvents
import com.example.memo.framework.presentation.main.language.LanguageViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun LanguageViewModel.deleteLanguages(pk: Int){
    baseState.value?.let { baseState ->
        deleteLanguageFromCacheUseCase.deleteLanguageFromCache(
            pk = pk
        ).onEach { dataState ->
            this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

            dataState.data?.let { response ->
                if(response.message == SuccessHandling.SUCCESS_DELETED){
                    onTriggerEvent(LanguageEvents.GetLanguagesEvent)
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