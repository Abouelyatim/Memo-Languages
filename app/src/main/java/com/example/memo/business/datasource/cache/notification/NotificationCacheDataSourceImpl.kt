package com.example.memo.business.datasource.cache.notification

import com.example.memo.business.domain.models.Notification
import com.example.memo.framework.datasource.cache.notification.NotificationDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationCacheDataSourceImpl
@Inject
constructor(
    private val notificationDaoService: NotificationDaoService
): NotificationCacheDataSource {

    override suspend fun insertNotifications(notifications: List<Notification>): List<Long> {
        return notificationDaoService.insertNotifications(notifications)
    }

    override suspend fun deleteNotification(pk: Int) {
        return notificationDaoService.deleteNotification(pk)
    }

    override suspend fun getAllNotifications(): List<Notification> {
        return notificationDaoService.getAllNotifications()
    }

    override suspend fun razeNotificationTable() {
        return notificationDaoService.razeNotificationTable()
    }

}