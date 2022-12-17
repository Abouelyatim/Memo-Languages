package com.example.memo.business.interactors.language

import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.handleUseCaseException
import com.example.memo.business.interactors.language.ports.GetLanguagesFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetLanguagesFromCache(
    private val cache: LanguageCacheDataSource,
) : GetLanguagesFromCacheUseCase {

    override fun getLanguagesFromCache(): Flow<DataState<List<Language>>> = flow{
        emit(DataState.loading<List<Language>>())
        val cachedLanguages = cache.getAllLanguages()
        emit(DataState.data(response = null, data = cachedLanguages))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}