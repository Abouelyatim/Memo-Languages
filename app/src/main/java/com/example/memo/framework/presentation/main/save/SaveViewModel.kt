package com.example.memo.framework.presentation.main.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.memo.business.datasource.datasource.AppDataStoreSource
import com.example.memo.business.interactors.notification.ports.DeleteNotificationFromCacheUseCase
import com.example.memo.business.interactors.notification.ports.GetNotificationsFromCacheUseCase
import com.example.memo.business.interactors.search.ports.*
import com.example.memo.framework.presentation.BaseEvents
import com.example.memo.framework.presentation.BaseViewModel
import com.example.memo.framework.presentation.main.save.events.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.sql.StatementEvent

@HiltViewModel
class SaveViewModel
@Inject
constructor(
    val getSearchesFromCacheUseCase: GetSearchesFromCacheUseCase,
    val insertSearchToCacheUseCase: InsertSearchToCacheUseCase,
    val deleteSearchFromCacheUseCase: DeleteSearchFromCacheUseCase,
    val appDataStoreSource: AppDataStoreSource,
    val savedStateHandle: SavedStateHandle,
    val deleteNotificationFromCacheUseCase: DeleteNotificationFromCacheUseCase,
    val getNotificationsFromCacheUseCase: GetNotificationsFromCacheUseCase,
    val updateSearchDismissFromCacheUseCase: UpdateSearchDismissFromCacheUseCase,
    val searchWordUseCase: SearchWordUseCase,
): BaseViewModel() {

    val state: MutableLiveData<SaveState> = MutableLiveData(SaveState())

    override fun onTriggerEvent(event: BaseEvents) {
        when (event) {

            is SaveEvents.OpenUrlOnBrowserEvent -> {
                openUrlOnBrowser(event.word,event.url)
            }

            is SaveEvents.UpdateSearchDismissEvent ->{
                updateSearchDismiss(event.pkSearch,event.oldDismiss)
            }

            is SaveEvents.DeleteNotificationEvent ->{
                deleteNotification(event.pk)
            }

            is SaveEvents.GetNotificationsEvent ->{
                getNotifications()
            }

            is SaveEvents.UpdateWordEvent -> {
                updateWord(event.word)
            }

            is SaveEvents.UpdateUrlEvent -> {
                updateUrl(event.url)
            }

            is SaveEvents.ReadUrlBundleEvent -> {
                readUrlBundle()
            }

            is SaveEvents.ReadWordDataStoreEvent -> {
                readWordDataStore()
            }

            is SaveEvents.DeleteSearchEvent -> {
                deleteSearch(event.pk)
            }

            is SaveEvents.InsertSearchEvent -> {
                insertSearch(event.word,event.url)
            }

            is SaveEvents.GetSearchesEvent -> {
                getSearches()
            }

            is SaveEvents.OnSavingCompletedEvent -> {
                onSavingCompleted(event.completed)
            }

            is SaveEvents.UpdateLanguageSourceEvent -> {
                updateLanguageSource(event.language)
            }

            is SaveEvents.UpdateLanguageDestinationEvent -> {
                updateLanguageDestination(event.language)
            }

            is SaveEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }

            is SaveEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }
}