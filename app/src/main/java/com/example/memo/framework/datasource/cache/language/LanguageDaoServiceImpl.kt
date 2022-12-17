package com.example.memo.framework.datasource.cache.language

import com.example.memo.business.domain.models.Language
import com.example.memo.framework.datasource.cache.language.database.LanguageDao
import com.example.memo.framework.datasource.cache.language.model.toEntity
import com.example.memo.framework.datasource.cache.language.model.toLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageDaoServiceImpl
@Inject
constructor(
    private val languageDao: LanguageDao
): LanguageDaoService {
    override suspend fun insertLanguage(language: Language): Long {
        return languageDao.insertLanguage(language.toEntity())}

    override suspend fun deleteLanguage(pk: Int) {
        return languageDao.deleteLanguage(pk)
    }

    override suspend fun updateLanguage(pk: Int, name: String) {
        return languageDao.updateLanguage(pk,name)
    }

    override suspend fun getAllLanguages(): List<Language> {
        return languageDao.getAllLanguages().map { it.toLanguage() }
    }
}