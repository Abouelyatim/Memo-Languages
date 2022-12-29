package com.example.memo.framework.datasource.cache.notification

import com.example.memo.business.domain.models.Notification

interface NotificationDaoService {

    suspend fun insertNotifications(notifications: List<Notification>): List<Long>

    suspend fun deleteNotification(pk: Int)

    suspend fun getAllNotifications(): List<Notification>

    suspend fun razeNotificationTable()
}