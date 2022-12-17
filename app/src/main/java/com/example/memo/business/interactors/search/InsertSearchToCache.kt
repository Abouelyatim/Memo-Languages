package com.example.memo.business.interactors.search

import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.domain.models.Search
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.search.ports.InsertSearchToCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class InsertSearchToCache (
    private val cache: SearchCacheDataSource,
) : InsertSearchToCacheUseCase {

    override fun insertSearchToCache(search: Search,show:Boolean): Flow<DataState<Response>> = flow {
        emit(DataState.loading<Response>())

        cache.insertSearch(
            search = search,
        )
        // Tell the UI it was successful
        val uIComponentType = if (show) UIComponentType.Dialog() else UIComponentType.None()
        emit(DataState.data<Response>(
            data = Response(
                message = SuccessHandling.SUCCESS_CREATED,
                uiComponentType = uIComponentType,
                messageType = MessageType.Success()
            ),
            response = null,
        ))

    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}