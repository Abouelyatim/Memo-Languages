package com.example.memo.di

import com.example.memo.business.datasource.cache.dictionary.DictionaryCacheDataSource
import com.example.memo.business.interactors.dictionary.DeleteDictionaryFromCache
import com.example.memo.business.interactors.dictionary.GetDictionariesFromCache
import com.example.memo.business.interactors.dictionary.InsertDictionaryToCache
import com.example.memo.business.interactors.dictionary.ports.DeleteDictionaryFromCacheUseCase
import com.example.memo.business.interactors.dictionary.ports.GetDictionariesFromCacheUseCase
import com.example.memo.business.interactors.dictionary.ports.InsertDictionaryToCacheUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {

    @Singleton
    @Provides
    fun provideDeleteDictionaryFromCache(
        dictionaryCacheDataSource: DictionaryCacheDataSource
    ): DeleteDictionaryFromCacheUseCase {
        return DeleteDictionaryFromCache(dictionaryCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideGetDictionariesFromCache(
        dictionaryCacheDataSource: DictionaryCacheDataSource
    ): GetDictionariesFromCacheUseCase {
        return GetDictionariesFromCache(dictionaryCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideInsertDictionaryToCache(
        dictionaryCacheDataSource: DictionaryCacheDataSource
    ): InsertDictionaryToCacheUseCase {
        return InsertDictionaryToCache(dictionaryCacheDataSource)
    }
}