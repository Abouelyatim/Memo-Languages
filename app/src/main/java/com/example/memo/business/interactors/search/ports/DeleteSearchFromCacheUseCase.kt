package com.example.memo.business.interactors.search.ports

import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface DeleteSearchFromCacheUseCase {

    fun deleteSearchFromCache(pk: Int): Flow<DataState<Response>>
}