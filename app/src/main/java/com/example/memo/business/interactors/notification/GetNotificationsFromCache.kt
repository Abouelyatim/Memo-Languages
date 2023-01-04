package com.example.memo.business.interactors.notification

import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSource
import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.datasource.datasource.AppDataStoreSource
import com.example.memo.business.domain.models.Notification
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.handleUseCaseException
import com.example.memo.business.interactors.notification.ports.GetNotificationsFromCacheUseCase
import com.example.memo.framework.presentation.util.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetNotificationsFromCache (
    private val cacheSearch: SearchCacheDataSource,
    private val cacheNotification: NotificationCacheDataSource,
    private val appDataStoreSource: AppDataStoreSource,
) : GetNotificationsFromCacheUseCase {

    override fun getNotificationsFromCache(): Flow<DataState<List<Notification>>>  = flow {
        emit(DataState.loading<List<Notification>>())

        var notificationNumber = 5
        var dismissNumber = 2
        appDataStoreSource.readValue(DataStoreKeys.NOTIFICATION_NUMBER)?.let {
            notificationNumber = it.toInt()
        }
        appDataStoreSource.readValue(DataStoreKeys.DISMISS_NUMBER)?.let {
            dismissNumber = it.toInt()
        }

        val cachedNotifications = cacheNotification.getAllNotifications()
        val missedNotificationsNumber = notificationNumber - cachedNotifications.size

        if (missedNotificationsNumber > 0){
            val allNewNotifications = cacheSearch.getAcceptedSearchesDismiss(dismissNumber)
                .map { it.toNotification() }
            if (allNewNotifications.isNotEmpty()){
                cacheNotification.razeNotificationTable()
                cacheNotification.insertNotifications(
                    allNewNotifications.take(notificationNumber)
                )
            }
        }

        val newCachedNotifications = cacheNotification.getAllNotifications()
        emit(DataState.data(response = null, data = newCachedNotifications))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}