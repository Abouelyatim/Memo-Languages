package com.example.memo.framework.datasource.cache.notification.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo.framework.datasource.cache.notification.model.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>): List<Long>

    @Query("DELETE FROM notification WHERE pk = :pk")
    suspend fun deleteNotification(pk: Int)

    @Query("""
        SELECT * FROM notification
        """)
    suspend fun getAllNotifications(): List<NotificationEntity>

    @Query("DELETE FROM notification")
    suspend fun razeNotificationTable()
}