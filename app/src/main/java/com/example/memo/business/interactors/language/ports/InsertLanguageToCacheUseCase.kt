package com.example.memo.business.interactors.language.ports

import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface InsertLanguageToCacheUseCase {

    fun insertLanguageToCache(language: Language): Flow<DataState<Response>>
}