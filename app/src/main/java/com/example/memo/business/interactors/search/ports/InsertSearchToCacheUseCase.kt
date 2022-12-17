package com.example.memo.business.interactors.search.ports

import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface InsertSearchToCacheUseCase {

    fun insertSearchToCache(search: Search,show:Boolean): Flow<DataState<Response>>
}