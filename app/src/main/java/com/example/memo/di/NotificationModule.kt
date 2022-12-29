package com.example.memo.di

import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSource
import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.interactors.notification.DeleteNotificationFromCache
import com.example.memo.business.interactors.notification.GetNotificationsFromCache
import com.example.memo.business.interactors.notification.ports.DeleteNotificationFromCacheUseCase
import com.example.memo.business.interactors.notification.ports.GetNotificationsFromCacheUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideDeleteNotificationFromCache(
        notificationCacheDataSource: NotificationCacheDataSource
    ): DeleteNotificationFromCacheUseCase {
        return DeleteNotificationFromCache(notificationCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideGetNotificationsFromCache(
        searchCacheDataSource: SearchCacheDataSource,
        notificationCacheDataSource: NotificationCacheDataSource
    ): GetNotificationsFromCacheUseCase {
        return GetNotificationsFromCache(searchCacheDataSource,notificationCacheDataSource)
    }
}