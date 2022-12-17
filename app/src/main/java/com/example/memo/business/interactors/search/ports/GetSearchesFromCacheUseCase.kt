package com.example.memo.business.interactors.search.ports

import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GetSearchesFromCacheUseCase {

    fun getSearchesFromCache(): Flow<DataState<List<Search>>>
}