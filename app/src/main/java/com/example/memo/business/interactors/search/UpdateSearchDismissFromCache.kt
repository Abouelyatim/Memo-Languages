package com.example.memo.business.interactors.search

import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.search.ports.UpdateSearchDismissFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UpdateSearchDismissFromCache (
    private val cache: SearchCacheDataSource,
) : UpdateSearchDismissFromCacheUseCase {

    override fun updateSearchDismissFromCache(pkSearch: Int, oldDismiss: Int): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.updateSearchDismiss(
            pk = pkSearch,
            dismiss = oldDismiss + 1
        )
        // Tell the UI it was successful
        emit(DataState.data<Response>(
            data = Response(
                message = SuccessHandling.SUCCESS_UPDATED,
                uiComponentType = UIComponentType.None(),
                messageType = MessageType.Success()
            ),
            response = null,
        ))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}