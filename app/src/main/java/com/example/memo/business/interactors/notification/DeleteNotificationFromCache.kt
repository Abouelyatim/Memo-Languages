package com.example.memo.business.interactors.notification

import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSource
import com.example.memo.business.domain.utils.*
import com.example.memo.business.interactors.notification.ports.DeleteNotificationFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DeleteNotificationFromCache (
    private val cache: NotificationCacheDataSource,
) : DeleteNotificationFromCacheUseCase {

    override fun deleteNotificationFromCache(pk: Int): Flow<DataState<Response>> = flow{
        emit(DataState.loading<Response>())

        cache.deleteNotification(
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