package com.example.memo.business.interactors.language

import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.domain.utils.SuccessHandling.Companion.SUCCESS_UPDATED
import com.example.memo.business.interactors.language.ports.UpdateLanguageFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UpdateLanguageFromCache (
    private val cache: LanguageCacheDataSource,
) : UpdateLanguageFromCacheUseCase {

    override fun updateLanguageFromCache(pk: Int, name: String): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.updateLanguage(
            pk = pk,
            name = name
        )
        // Tell the UI it was successful
        emit(DataState.data<Response>(
            data = Response(
                message = SUCCESS_UPDATED,
                uiComponentType = UIComponentType.None(),
                messageType = MessageType.Success()
            ),
            response = null,
        ))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}