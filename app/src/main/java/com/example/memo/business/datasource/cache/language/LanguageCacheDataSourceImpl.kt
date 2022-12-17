package com.example.memo.business.datasource.cache.language

import com.example.memo.business.domain.models.Language
import com.example.memo.framework.datasource.cache.language.LanguageDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageCacheDataSourceImpl
@Inject
constructor(
    private val languageDaoService: LanguageDaoService
): LanguageCacheDataSource {
    override suspend fun insertLanguage(language: Language): Long {
        return languageDaoService.insertLanguage(language)
    }

    override suspend fun deleteLanguage(pk: Int) {
        return languageDaoService.deleteLanguage(pk)
    }

    override suspend fun updateLanguage(pk: Int, name: String) {
        return languageDaoService.updateLanguage(pk,name)
    }

    override suspend fun getAllLanguages(): List<Language> {
        return languageDaoService.getAllLanguages()
    }

}