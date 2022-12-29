package com.example.memo.framework.datasource.cache.notification.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memo.business.domain.models.Notification

@Entity(tableName = "notification")
class NotificationEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val pk: Int?,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "dismiss")
    val dismiss: Int,

    @ColumnInfo(name = "pk_search")
    val pkSearch: Int,
)

fun NotificationEntity.toNotification(): Notification {
    return Notification(
        pk = pk,
        word = word,
        url = url,
        dismiss = dismiss,
        pkSearch = pkSearch,
    )
}

fun Notification.toEntity(): NotificationEntity {
    return NotificationEntity(
        pk = pk,
        word = word,
        url = url,
        dismiss = dismiss,
        pkSearch = pkSearch,
    )
}