package com.example.memo.framework.presentation.main.language

import com.example.memo.business.domain.models.Language

data class LanguageState (
    val languageList: List<Language> = listOf(),
)