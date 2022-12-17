package com.example.memo.business.interactors.dictionary.ports

import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GetDictionariesFromCacheUseCase {

    fun getDictionariesFromCache(): Flow<DataState<List<Dictionary>>>
}