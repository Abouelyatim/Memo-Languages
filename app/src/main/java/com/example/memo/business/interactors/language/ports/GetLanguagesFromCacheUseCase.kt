package com.example.memo.business.interactors.language.ports

import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GetLanguagesFromCacheUseCase {

    fun getLanguagesFromCache(): Flow<DataState<List<Language>>>
}