package com.example.memo.framework.presentation.main.save

import com.example.memo.business.domain.models.Notification
import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.Queue
import com.example.memo.business.domain.utils.StateMessage

data class SaveState (
    val languageSource: String = "",
    val languageDestination: String = "",
    val word: String = "",
    val url: String = "",
    val searchList: List<Search> = listOf(),
    val isSavingCompleted: Boolean = false,
    val closeActivity: Boolean = false,
    val notifications: List<Notification> = listOf(),
)