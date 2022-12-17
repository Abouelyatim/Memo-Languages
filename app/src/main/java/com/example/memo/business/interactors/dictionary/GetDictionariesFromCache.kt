package com.example.memo.business.interactors.dictionary

import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSource
import com.example.memo.business.domain.models.Dictionary
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.handleUseCaseException
import com.example.memo.business.interactors.dictionary.ports.GetDictionariesFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetDictionariesFromCache(
    private val cache: DictionaryCacheDataSource
): GetDictionariesFromCacheUseCase {

    override fun getDictionariesFromCache(): Flow<DataState<List<Dictionary>>> = flow{
        emit(DataState.loading<List<Dictionary>>())
        val cachedDictionaries = cache.getAllDictionaries()
        emit(DataState.data(response = null, data = cachedDictionaries))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}