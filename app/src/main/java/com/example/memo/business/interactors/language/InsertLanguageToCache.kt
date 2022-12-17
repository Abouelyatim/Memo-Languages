package com.example.memo.business.interactors.language

import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.domain.models.Language
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.language.ports.InsertLanguageToCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class InsertLanguageToCache (
    private val cache: LanguageCacheDataSource,
) : InsertLanguageToCacheUseCase {

    override fun insertLanguageToCache(language: Language): Flow<DataState<Response>> = flow {
        emit(DataState.loading<Response>())

        if(!language.isValid()){
            throw Exception(ErrorHandling.ERROR_MUST_NOT_EMPTY_OR_BLANC)
        }

        cache.insertLanguage(
            language = language,
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