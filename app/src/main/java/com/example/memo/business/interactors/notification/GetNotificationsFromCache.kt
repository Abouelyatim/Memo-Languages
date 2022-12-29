package com.example.memo.business.interactors.notification

import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSource
import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.domain.models.Notification
import com.example.memo.business.domain.utils.DataState
import com.example.memo.business.domain.utils.handleUseCaseException
import com.example.memo.business.interactors.notification.ports.GetNotificationsFromCacheUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetNotificationsFromCache (
    private val cacheSearch: SearchCacheDataSource,
    private val cacheNotification: NotificationCacheDataSource,
) : GetNotificationsFromCacheUseCase {

    override fun getNotificationsFromCache(): Flow<DataState<List<Notification>>>  = flow {
        emit(DataState.loading<List<Notification>>())

        val cachedNotifications = cacheNotification.getAllNotifications()
        val missedNotificationsNumber = 5 - cachedNotifications.size//TODO set 5 like constant

        if (missedNotificationsNumber > 0){
            val allNewNotifications = cacheSearch.getAcceptedSearchesDismiss(2)//TODO set 2 like constant
                .map { it.toNotification() }
            if (allNewNotifications.isNotEmpty()){
                cacheNotification.razeNotificationTable()
                cacheNotification.insertNotifications(
                    allNewNotifications.take(missedNotificationsNumber)
                )
            }
        }

        val newCachedNotifications = cacheNotification.getAllNotifications()
        emit(DataState.data(response = null, data = newCachedNotifications))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}