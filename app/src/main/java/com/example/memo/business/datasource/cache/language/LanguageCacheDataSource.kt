package com.example.memo.business.datasource.cache.language

import com.example.memo.business.domain.models.Language

interface LanguageCacheDataSource {
    suspend fun insertLanguage(language: Language): Long

    suspend fun deleteLanguage(pk: Int)

    suspend fun updateLanguage(pk: Int, name: String)

    suspend fun getAllLanguages(): List<Language>
}