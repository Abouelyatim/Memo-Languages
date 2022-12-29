package com.example.memo.business.interactors.notification.ports

import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface DeleteNotificationFromCacheUseCase {

    fun deleteNotificationFromCache(pk: Int): Flow<DataState<Response>>
}