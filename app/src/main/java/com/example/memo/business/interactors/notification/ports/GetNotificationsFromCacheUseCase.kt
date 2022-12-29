package com.example.memo.business.interactors.notification.ports

import com.example.memo.business.domain.models.Notification
import com.example.memo.business.domain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GetNotificationsFromCacheUseCase {

    fun getNotificationsFromCache(): Flow<DataState<List<Notification>>>
}