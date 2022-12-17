package com.example.memo.framework.presentation.main.language

import androidx.lifecycle.MutableLiveData
import com.example.memo.business.interactors.language.ports.DeleteLanguageFromCacheUseCase
import com.example.memo.business.interactors.language.ports.GetLanguagesFromCacheUseCase
import com.example.memo.business.interactors.language.ports.InsertLanguageToCacheUseCase
import com.example.memo.framework.presentation.BaseEvents
import com.example.memo.framework.presentation.BaseViewModel
import com.example.memo.framework.presentation.main.language.events.deleteLanguages
import com.example.memo.framework.presentation.main.language.events.getLanguages
import com.example.memo.framework.presentation.main.language.events.insertLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel
@Inject
constructor(
    val getLanguagesFromCacheUseCase: GetLanguagesFromCacheUseCase,
    val insertLanguageToCacheUseCase: InsertLanguageToCacheUseCase,
    val deleteLanguageFromCacheUseCase: DeleteLanguageFromCacheUseCase
): BaseViewModel() {

    val state: MutableLiveData<LanguageState> = MutableLiveData(LanguageState())

    override fun onTriggerEvent(event: BaseEvents) {
        when (event) {

            is LanguageEvents.DeleteLanguageEvent -> {
                deleteLanguages(
                    pk = event.pk
                )
            }

            is LanguageEvents.InsertLanguageEvent -> {
                insertLanguage(
                    name = event.name
                )
            }

            is LanguageEvents.GetLanguagesEvent -> {
                getLanguages()
            }

            is LanguageEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }

            is LanguageEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }
}