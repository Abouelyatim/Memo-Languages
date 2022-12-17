package com.example.memo.business.interactors.language

import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.language.ports.DeleteLanguageFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DeleteLanguageFromCache (
    private val cache: LanguageCacheDataSource,
) : DeleteLanguageFromCacheUseCase {

    override fun deleteLanguageFromCache(pk: Int): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.deleteLanguage(
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