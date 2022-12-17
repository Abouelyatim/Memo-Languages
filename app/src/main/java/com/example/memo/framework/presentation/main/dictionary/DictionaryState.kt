package com.example.memo.framework.presentation.main.dictionary

import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.Queue
import com.example.memo.business.domain.utils.StateMessage

data class DictionaryState(
    val dictionaryList: List<Dictionary> = listOf(),
)