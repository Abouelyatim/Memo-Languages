package com.example.memo.framework.datasource.cache.search

import com.example.memo.business.domain.models.Search

interface SearchDaoService {

    suspend fun insertSearch(search: Search): Long

    suspend fun deleteSearch(pk: Int)

    suspend fun getAllSearches(): List<Search>

    suspend fun updateSearchDismiss(pk: Int, dismiss: Int)

    suspend fun getAcceptedSearchesDismiss(acceptedDismiss: Int): List<Search>
}