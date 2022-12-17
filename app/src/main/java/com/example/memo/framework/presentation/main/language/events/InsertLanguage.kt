package com.example.memo.framework.presentation.main.language.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.SuccessHandling
import com.example.memo.framework.presentation.main.language.LanguageEvents
import com.example.memo.framework.presentation.main.language.LanguageViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun LanguageViewModel.insertLanguage(name: String){
    baseState.value?.let { baseState ->
        insertLanguageToCacheUseCase.insertLanguageToCache(
            language = Language(
                pk= null,
                name= name
            )
        ).onEach { dataState ->
            this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

            dataState.data?.let { response ->
                if(response.message == SuccessHandling.SUCCESS_CREATED){
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