package com.example.memo.di

import com.example.memo.business.datasource.cache.language.LanguageCacheDataSource
import com.example.memo.business.interactors.language.DeleteLanguageFromCache
import com.example.memo.business.interactors.language.GetLanguagesFromCache
import com.example.memo.business.interactors.language.InsertLanguageToCache
import com.example.memo.business.interactors.language.UpdateLanguageFromCache
import com.example.memo.business.interactors.language.ports.DeleteLanguageFromCacheUseCase
import com.example.memo.business.interactors.language.ports.GetLanguagesFromCacheUseCase
import com.example.memo.business.interactors.language.ports.InsertLanguageToCacheUseCase
import com.example.memo.business.interactors.language.ports.UpdateLanguageFromCacheUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LanguageModule {

    @Singleton
    @Provides
    fun provideDeleteLanguageFromCache(
        languageCacheDataSource: LanguageCacheDataSource
    ): DeleteLanguageFromCacheUseCase {
        return DeleteLanguageFromCache(languageCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideGetLanguagesFromCache(
        languageCacheDataSource: LanguageCacheDataSource
    ): GetLanguagesFromCacheUseCase {
        return GetLanguagesFromCache(languageCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideInsertLanguageToCache(
        languageCacheDataSource: LanguageCacheDataSource
    ): InsertLanguageToCacheUseCase {
        return InsertLanguageToCache(languageCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideUpdateLanguageFromCache(
        languageCacheDataSource: LanguageCacheDataSource
    ): UpdateLanguageFromCacheUseCase {
        return UpdateLanguageFromCache(languageCacheDataSource)
    }
}