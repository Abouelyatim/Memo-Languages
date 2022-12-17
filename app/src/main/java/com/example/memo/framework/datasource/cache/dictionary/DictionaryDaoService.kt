package com.example.memo.framework.datasource.cache.dictionary

import com.example.memo.business.domain.models.Dictionary

interface DictionaryDaoService {

    suspend fun insertDictionary(dictionary: Dictionary): Long

    suspend fun deleteDictionary(pk: Int)

    suspend fun getAllDictionaries(): List<Dictionary>
}