package com.example.memo.di

import android.app.Application
import com.example.memo.business.datasource.cache.search.SearchCacheDataSource
import com.example.memo.business.interactors.search.DeleteSearchFromCache
import com.example.memo.business.interactors.search.GetSearchesFromCache
import com.example.memo.business.interactors.search.InsertSearchToCache
import com.example.memo.business.interactors.search.SearchWord
import com.example.memo.business.interactors.search.ports.DeleteSearchFromCacheUseCase
import com.example.memo.business.interactors.search.ports.GetSearchesFromCacheUseCase
import com.example.memo.business.interactors.search.ports.InsertSearchToCacheUseCase
import com.example.memo.business.interactors.search.ports.SearchWordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideDeleteSearchFromCache(
        searchCacheDataSource: SearchCacheDataSource
    ): DeleteSearchFromCacheUseCase {
        return DeleteSearchFromCache(searchCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideGetSearchesFromCache(
        searchCacheDataSource: SearchCacheDataSource
    ): GetSearchesFromCacheUseCase {
        return GetSearchesFromCache(searchCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideInsertSearchToCache(
        searchCacheDataSource: SearchCacheDataSource
    ): InsertSearchToCacheUseCase {
        return InsertSearchToCache(searchCacheDataSource)
    }

    @Singleton
    @Provides
    fun provideSearchWord(
        app: Application
    ): SearchWordUseCase {
        return SearchWord(app)
    }
}