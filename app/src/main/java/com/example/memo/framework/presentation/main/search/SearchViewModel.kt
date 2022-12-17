package com.example.memo.framework.presentation.main.search

import androidx.lifecycle.MutableLiveData
import com.example.memo.business.datasource.datasource.AppDataStoreSource
import com.example.memo.business.interactors.search.ports.SearchWordUseCase
import com.example.memo.framework.presentation.BaseEvents
import com.example.memo.framework.presentation.BaseViewModel
import com.example.memo.framework.presentation.main.search.events.saveWordDataStoreEvent
import com.example.memo.framework.presentation.main.search.events.searchWord
import com.example.memo.framework.presentation.main.search.events.updateCurrentUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    val searchWordUseCase: SearchWordUseCase,
    val appDataStoreSource: AppDataStoreSource,
): BaseViewModel() {

    val state: MutableLiveData<SearchState> = MutableLiveData(SearchState())

    override fun onTriggerEvent(event: BaseEvents) {
        when (event) {

            is SearchEvents.SaveWordDataStoreEvent ->{
                saveWordDataStoreEvent(event.word)
            }

            is SearchEvents.SearchWordEvent -> {
                searchWord(
                    word = event.word
                )
            }

            is SearchEvents.UpdateCurrentUrlEvent -> {
                updateCurrentUrl(
                    url = event.url
                )
            }

            is SearchEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }

            is SearchEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }
}