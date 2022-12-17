package com.example.memo.framework.datasource.cache.language

import com.example.memo.business.domain.models.Language

interface LanguageDaoService {

    suspend fun insertLanguage(language: Language): Long

    suspend fun deleteLanguage(pk: Int)

    suspend fun updateLanguage(pk: Int, name: String)

    suspend fun getAllLanguages(): List<Language>
}