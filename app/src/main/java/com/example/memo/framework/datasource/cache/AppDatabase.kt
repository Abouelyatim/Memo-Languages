package com.example.memo.framework.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memo.framework.datasource.cache.dictionary.database.DictionaryDao
import com.example.memo.framework.datasource.cache.dictionary.model.DictionaryEntity
import com.example.memo.framework.datasource.cache.language.database.LanguageDao
import com.example.memo.framework.datasource.cache.language.model.LanguageEntity
import com.example.memo.framework.datasource.cache.notification.database.NotificationDao
import com.example.memo.framework.datasource.cache.notification.model.NotificationEntity
import com.example.memo.framework.datasource.cache.search.database.SearchDao
import com.example.memo.framework.datasource.cache.search.model.SearchEntity

@Database(
    version = 4,
    entities = [ LanguageEntity::class, DictionaryEntity::class, SearchEntity::class, NotificationEntity::class],
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLanguageDao(): LanguageDao

    abstract fun getDictionaryDao(): DictionaryDao

    abstract fun getSearchDao(): SearchDao

    abstract fun getNotificationDao(): NotificationDao

    companion object{
        val DATABASE_NAME: String = "app_db"
    }
}