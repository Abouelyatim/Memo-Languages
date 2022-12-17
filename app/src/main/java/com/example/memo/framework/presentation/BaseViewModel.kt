package com.example.memo.framework.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memo.business.domain.utils.StateMessage
import com.example.memo.business.domain.utils.UIComponentType
import com.example.memo.business.domain.utils.doesMessageAlreadyExistInQueue

abstract class BaseViewModel: ViewModel() {
    val TAG: String = "AppDebug"

    val baseState: MutableLiveData<BaseState> = MutableLiveData(BaseState())

    abstract fun onTriggerEvent(event: BaseEvents)

    fun removeHeadFromQueue() {
        baseState.value?.let { state ->
            try {
                val queue = state.queue
                queue.remove() // can throw exception if empty
                this.baseState.value = state.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }

    fun appendToMessageQueue(stateMessage: StateMessage) {
        baseState.value?.let { state ->
            val queue = state.queue
            if(!stateMessage.doesMessageAlreadyExistInQueue(queue = queue)){
                if(!(stateMessage.response.uiComponentType is UIComponentType.None)){
                    queue.add(stateMessage)
                    this.baseState.value = state.copy(queue = queue)
                }
            }
        }
    }
}