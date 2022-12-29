package com.example.memo.di

import android.app.Application
import androidx.room.Room
import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSource
import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSourceImpl
import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.datasource.cache.language.LanguageCacheDataSourceImpl
import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSource
import com.example.memo.business.datasource.cache.notification.NotificationCacheDataSourceImpl
import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.datasource.cache.search.SearchCacheDataSourceImpl
import com.example.memo.business.datasource.datasource.AppDataStoreSource
import com.example.memo.business.datasource.datasource.AppDataStoreSourceImpl
import com.example.memo.framework.datasource.cache.AppDatabase
import com.example.memo.framework.datasource.cache.AppDatabase.Companion.DATABASE_NAME
import com.example.memo.framework.datasource.cache.dictionary.DictionaryDaoService
import com.example.memo.framework.datasource.cache.dictionary.DictionaryDaoServiceImpl
import com.example.memo.framework.datasource.cache.dictionary.database.DictionaryDao
import com.example.memo.framework.datasource.cache.language.LanguageDaoService
import com.example.memo.framework.datasource.cache.language.LanguageDaoServiceImpl
import com.example.memo.framework.datasource.cache.language.database.LanguageDao
import com.example.memo.framework.datasource.cache.notification.NotificationDaoService
import com.example.memo.framework.datasource.cache.notification.NotificationDaoServiceImpl
import com.example.memo.framework.datasource.cache.notification.database.NotificationDao
import com.example.memo.framework.datasource.cache.search.SearchDaoService
import com.example.memo.framework.datasource.cache.search.SearchDaoServiceImpl
import com.example.memo.framework.datasource.cache.search.database.SearchDao
import com.example.memo.framework.datasource.datastore.AppDataStoreService
import com.example.memo.framework.datasource.datastore.AppDataStoreServiceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(
        application: Application
    ): AppDataStoreService {
        return AppDataStoreServiceManager(application)
    }

    @Singleton
    @Provides
    fun provideAppDataStoreSource(
        appDataStore: AppDataStoreService
    ): AppDataStoreSource {
        return AppDataStoreSourceImpl(appDataStore)
    }

    //Language providers
    @Singleton
    @Provides
    fun provideLanguageDao(db: AppDatabase): LanguageDao {
        return db.getLanguageDao()
    }

    @Singleton
    @Provides
    fun provideLanguageCacheDataSource(
        languageDaoService: LanguageDaoService
    ): LanguageCacheDataSource {
        return LanguageCacheDataSourceImpl(languageDaoService)
    }

    @Singleton
    @Provides
    fun provideLanguageDaoService(
        languageDao: LanguageDao
    ): LanguageDaoService {
        return LanguageDaoServiceImpl(languageDao)
    }

    //Dictionary providers
    @Singleton
    @Provides
    fun provideDictionaryDao(db: AppDatabase): DictionaryDao {
        return db.getDictionaryDao()
    }

    @Singleton
    @Provides
    fun provideDictionaryCacheDataSource(
        dictionaryDaoService: DictionaryDaoService
    ): DictionaryCacheDataSource {
        return DictionaryCacheDataSourceImpl(dictionaryDaoService)
    }

    @Singleton
    @Provides
    fun provideDictionaryDaoService(
        dictionaryDao: DictionaryDao
    ): DictionaryDaoService {
        return DictionaryDaoServiceImpl(dictionaryDao)
    }

    //Search providers
    @Singleton
    @Provides
    fun provideSearchDao(db: AppDatabase): SearchDao {
        return db.getSearchDao()
    }

    @Singleton
    @Provides
    fun provideSearchCacheDataSource(
        searchDaoService: SearchDaoService
    ): SearchCacheDataSource {
        return SearchCacheDataSourceImpl(searchDaoService)
    }

    @Singleton
    @Provides
    fun provideSearchDaoService(
        searchDao: SearchDao
    ): SearchDaoService {
        return SearchDaoServiceImpl(searchDao)
    }

    //Notification providers
    @Singleton
    @Provides
    fun provideNotificationDao(db: AppDatabase): NotificationDao {
        return db.getNotificationDao()
    }

    @Singleton
    @Provides
    fun provideNotificationCacheDataSource(
        notificationDaoService: NotificationDaoService
    ): NotificationCacheDataSource {
        return NotificationCacheDataSourceImpl(notificationDaoService)
    }

    @Singleton
    @Provides
    fun provideNotificationDaoService(
        notificationDao: NotificationDao
    ): NotificationDaoService {
        return NotificationDaoServiceImpl(notificationDao)
    }
}