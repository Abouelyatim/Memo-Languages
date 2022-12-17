package com.example.memo.business.interactors.dictionary

import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.dictionary.ports.DeleteDictionaryFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DeleteDictionaryFromCache(
    private val cache: DictionaryCacheDataSource
): DeleteDictionaryFromCacheUseCase {

    override fun deleteDictionaryFromCache(pk: Int): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.deleteDictionary(
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