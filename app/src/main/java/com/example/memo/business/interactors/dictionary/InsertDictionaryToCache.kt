package com.example.memo.business.interactors.dictionary

import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSource
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.dictionary.ports.InsertDictionaryToCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class InsertDictionaryToCache (
    private val cache: DictionaryCacheDataSource
): InsertDictionaryToCacheUseCase {

    override fun insertDictionaryToCache(dictionary: Dictionary): Flow<DataState<Response>> = flow {
        emit(DataState.loading<Response>())

        if(!dictionary.isValid()){
            throw Exception(ErrorHandling.ERROR_MUST_NOT_EMPTY_OR_BLANC)
        }

        cache.insertDictionary(
            dictionary = dictionary,
        )
        // Tell the UI it was successful
        emit(DataState.data<Response>(
            data = Response(
                message = SuccessHandling.SUCCESS_CREATED,
                uiComponentType = UIComponentType.None(),
                messageType = MessageType.Success()
            ),
            response = null,
        ))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}