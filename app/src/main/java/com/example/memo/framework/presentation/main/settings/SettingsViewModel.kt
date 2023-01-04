package com.example.memo.framework.presentation.main.settings

import androidx.lifecycle.MutableLiveData
import com.example.memo.business.datasource.datasource.AppDataStoreSource
import com.example.memo.framework.presentation.BaseEvents
import com.example.memo.framework.presentation.BaseViewModel
import com.example.memo.framework.presentation.main.settings.events.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    val appDataStoreSource: AppDataStoreSource,
): BaseViewModel() {

    val state: MutableLiveData<SettingsState> = MutableLiveData(SettingsState())


    override fun onTriggerEvent(event: BaseEvents) {
        when (event){
            is SettingsEvents.ReadSettingsDataStoreEvent ->{
                readSettingsDataStore()
            }

            is SettingsEvents.UpdateNotificationNumberEvent ->{
                updateNotificationNumber(event.notificationNumber)
            }

            is SettingsEvents.UpdateDismissNumberEvent ->{
                updateDismissNumber(event.dismissNumber)
            }

            is SettingsEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }

            is SettingsEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }
}