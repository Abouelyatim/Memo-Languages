package com.example.memo.framework.presentation.main.dictionary

import androidx.lifecycle.MutableLiveData
import com.example.memo.business.interactors.dictionary.ports.DeleteDictionaryFromCacheUseCase
import com.example.memo.business.interactors.dictionary.ports.GetDictionariesFromCacheUseCase
import com.example.memo.business.interactors.dictionary.ports.InsertDictionaryToCacheUseCase
import com.example.memo.framework.presentation.BaseEvents
import com.example.memo.framework.presentation.BaseViewModel
import com.example.memo.framework.presentation.main.dictionary.events.deleteDictionary
import com.example.memo.framework.presentation.main.dictionary.events.getDictionaries
import com.example.memo.framework.presentation.main.dictionary.events.insertDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel
@Inject
constructor(
    val getDictionariesFromCacheUseCase: GetDictionariesFromCacheUseCase,
    val insertDictionaryToCacheUseCase: InsertDictionaryToCacheUseCase,
    val deleteDictionaryFromCacheUseCase: DeleteDictionaryFromCacheUseCase
): BaseViewModel() {

    val state: MutableLiveData<DictionaryState> = MutableLiveData(DictionaryState())

    override fun onTriggerEvent(event: BaseEvents) {
        when (event) {

            is DictionaryEvents.DeleteDictionaryEvent -> {
                deleteDictionary(
                    pk = event.pk
                )
            }

            is DictionaryEvents.InsertDictionaryEvent -> {
                insertDictionary(
                    name = event.name,
                    url = event.url
                )
            }

            is DictionaryEvents.GetDictionariesEvent -> {
                getDictionaries()
            }

            is DictionaryEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }

            is DictionaryEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }
}