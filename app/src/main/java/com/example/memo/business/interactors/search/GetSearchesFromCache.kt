package com.example.memo.business.interactors.search

import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.handleUseCaseException
import com.example.memo.business.interactors.search.ports.GetSearchesFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetSearchesFromCache (
    private val cache: SearchCacheDataSource,
) : GetSearchesFromCacheUseCase {

    override fun getSearchesFromCache(): Flow<DataState<List<Search>>> = flow {
        emit(DataState.loading<List<Search>>())
        val cachedSearches = cache.getAllSearches()
        emit(DataState.data(response = null, data = cachedSearches))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}