package com.example.memo.business.datasource.cache.dictionary

import com.example.memo.business.domain.models.Dictionary
import com.example.memo.framework.datasource.cache.dictionary.DictionaryDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryCacheDataSourceImpl
@Inject
constructor(
    private val dictionaryDaoService: DictionaryDaoService
): DictionaryCacheDataSource {
    override suspend fun insertDictionary(dictionary: Dictionary): Long {
        return dictionaryDaoService.insertDictionary(dictionary)
    }

    override suspend fun deleteDictionary(pk: Int) {
        return dictionaryDaoService.deleteDictionary(pk)
    }

    override suspend fun getAllDictionaries(): List<Dictionary> {
        return dictionaryDaoService.getAllDictionaries()
    }
}