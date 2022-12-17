package com.example.memo.framework.datasource.cache.search

import com.example.memo.business.domain.models.Search
import com.example.memo.framework.datasource.cache.search.database.SearchDao
import com.example.memo.framework.datasource.cache.search.model.toEntity
import com.example.memo.framework.datasource.cache.search.model.toSearch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchDaoServiceImpl
@Inject
constructor(
    private val searchDao: SearchDao
): SearchDaoService {

    override suspend fun insertSearch(search: Search): Long {
        return searchDao.insertSearch(search.toEntity())
    }

    override suspend fun deleteSearch(pk: Int) {
        return searchDao.deleteSearch(pk)
    }

    override suspend fun getAllSearches(): List<Search> {
        return searchDao.getAllSearches().map { it.toSearch() }
    }
}