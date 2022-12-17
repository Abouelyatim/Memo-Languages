package com.example.memo.business.interactors.search

import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.search.ports.DeleteSearchFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DeleteSearchFromCache (
    private val cache: SearchCacheDataSource,
) : DeleteSearchFromCacheUseCase {

    override fun deleteSearchFromCache(pk: Int): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.deleteSearch(
            pk = pk,
        )
        // Tell the UI it was successful
        emit(DataState.data<Response>(
            data = Response(
                message = SuccessHandling.SUCCESS_DELETED,
                uiComponentType = UIComponentType.None(),
                messageType = MessageType.Success()
            ),
            response = null,
        ))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}