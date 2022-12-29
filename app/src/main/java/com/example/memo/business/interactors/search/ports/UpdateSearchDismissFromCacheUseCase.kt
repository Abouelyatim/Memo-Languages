package com.example.memo.business.interactors.search.ports

import com.example.memo.business.domain.models.Notification
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface UpdateSearchDismissFromCacheUseCase {

    fun updateSearchDismissFromCache(pkSearch: Int, oldDismiss: Int): Flow<DataState<Response>>
}