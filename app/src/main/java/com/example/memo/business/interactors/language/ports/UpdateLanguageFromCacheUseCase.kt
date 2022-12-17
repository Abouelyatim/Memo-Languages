package com.example.memo.business.interactors.language.ports

import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface UpdateLanguageFromCacheUseCase {

    fun updateLanguageFromCache(pk: Int, name: String): Flow<DataState<Response>>
}