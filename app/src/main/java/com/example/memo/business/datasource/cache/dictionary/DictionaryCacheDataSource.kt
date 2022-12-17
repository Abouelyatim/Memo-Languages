package com.example.memo.business.datasource.cache.dictionary

import com.example.memo.business.domain.models.Dictionary

interface DictionaryCacheDataSource {

    suspend fun insertDictionary(dictionary: Dictionary): Long

    suspend fun deleteDictionary(pk: Int)

    suspend fun getAllDictionaries(): List<Dictionary>
}