package com.example.memo.business.datasource.cache.search

import com.example.memo.business.domain.models.Search

interface SearchCacheDataSource {

    suspend fun insertSearch(search: Search): Long

    suspend fun deleteSearch(pk: Int)

    suspend fun getAllSearches(): List<Search>
}