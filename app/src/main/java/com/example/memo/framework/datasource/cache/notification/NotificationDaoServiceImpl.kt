package com.example.memo.framework.datasource.cache.notification

import com.example.memo.business.domain.models.Notification
import com.example.memo.framework.datasource.cache.notification.database.NotificationDao
import com.example.memo.framework.datasource.cache.notification.model.toEntity
import com.example.memo.framework.datasource.cache.notification.model.toNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDaoServiceImpl
@Inject
constructor(
    private val notificationDao: NotificationDao
): NotificationDaoService {

    override suspend fun insertNotifications(notifications: List<Notification>): List<Long> {
        return notificationDao.insertNotifications(notifications.map { it.toEntity() })
    }

    override suspend fun deleteNotification(pk: Int) {
        return notificationDao.deleteNotification(pk)
    }

    override suspend fun getAllNotifications(): List<Notification> {
        return notificationDao.getAllNotifications().map { it.toNotification() }
    }

    override suspend fun razeNotificationTable() {
        return notificationDao.razeNotificationTable()
    }
}