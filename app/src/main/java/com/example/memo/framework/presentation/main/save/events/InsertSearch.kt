package com.example.memo.framework.presentation.main.save.events

import androidx.lifecycle.viewModelScope
import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.*
import com.example.memo.framework.presentation.main.save.SaveViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun SaveViewModel.insertSearch(word: String, url:String){
    baseState.value?.let { baseState ->
        state.value?.let { state ->
            val search = Search(
                pk = null,
                word = word,
                languageSource = state.languageSource,
                languageDestination = state.languageDestination,
                url = url,
                dismiss = 0
            )
            if(search.isValid()){//this validation should be inside interactor to centralize logic so easy to test
                insertSearchToCacheUseCase.insertSearchToCache(
                    search = search,
                    show = !state.closeActivity
                ).onEach { dataState ->
                    this.baseState.value = baseState.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { response ->
                        if(response.message == SuccessHandling.SUCCESS_CREATED){
                            onTriggerEvent(com.example.memo.framework.presentation.main.save.SaveEvents.OnSavingCompletedEvent(true))
                        }
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
            else{
                onTriggerEvent(
                    com.example.memo.framework.presentation.main.save.SaveEvents.Error(
                        stateMessage = StateMessage(
                            response = Response(
                                message = ErrorHandling.ERROR_MUST_NOT_EMPTY_OR_BLANC,
                                uiComponentType = UIComponentType.Toast(),
                                messageType = MessageType.Error()
                            )
                        )
                    )
                )
            }
        }
    }
}