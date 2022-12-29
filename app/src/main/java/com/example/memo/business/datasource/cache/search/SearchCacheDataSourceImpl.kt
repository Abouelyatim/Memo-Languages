package com.example.memo.business.datasource.cache.search

import com.example.memo.business.domain.models.Search
import com.example.memo.framework.datasource.cache.search.SearchDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchCacheDataSourceImpl
@Inject
constructor(
    private val searchDaoService: SearchDaoService
): SearchCacheDataSource {
    override suspend fun insertSearch(search: Search): Long {
        return searchDaoService.insertSearch(search)
    }

    override suspend fun deleteSearch(pk: Int) {
        return searchDaoService.deleteSearch(pk)
    }

    override suspend fun getAllSearches(): List<Search> {
        return searchDaoService.getAllSearches()
    }

    override suspend fun updateSearchDismiss(pk: Int, dismiss: Int) {
        return searchDaoService.updateSearchDismiss(pk,dismiss)
    }

    override suspend fun getAcceptedSearchesDismiss(acceptedDismiss: Int): List<Search> {
        return searchDaoService.getAcceptedSearchesDismiss(acceptedDismiss)
    }
}