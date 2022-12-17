package com.example.memo.framework.datasource.cache.dictionary

import com.example.memo.business.domain.models.Dictionary
import com.example.memo.framework.datasource.cache.dictionary.database.DictionaryDao
import com.example.memo.framework.datasource.cache.dictionary.model.toDictionary
import com.example.memo.framework.datasource.cache.dictionary.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryDaoServiceImpl
@Inject
constructor(
    private val dictionaryDao : DictionaryDao
): DictionaryDaoService {

    override suspend fun insertDictionary(dictionary: Dictionary): Long {
        return dictionaryDao.insertDictionary(dictionary.toEntity())
    }

    override suspend fun deleteDictionary(pk: Int) {
        return dictionaryDao.deleteDictionary(pk)
    }

    override suspend fun getAllDictionaries(): List<Dictionary> {
        return dictionaryDao.getAllDictionaries().map { it.toDictionary() }
    }
}