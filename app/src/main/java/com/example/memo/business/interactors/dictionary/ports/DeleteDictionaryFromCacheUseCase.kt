package com.example.memo.business.interactors.dictionary.ports

import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface DeleteDictionaryFromCacheUseCase {

    fun deleteDictionaryFromCache(pk: Int): Flow<DataState<Response>>
}