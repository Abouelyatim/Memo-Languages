package com.example.memo.framework.presentation

import com.example.memo.business.domain.utils.Queue
import com.example.memo.business.domain.utils.StateMessage

data class BaseState (
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)