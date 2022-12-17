package com.example.memo.framework.datasource.cache.language.database

import androidx.room.*
import com.example.memo.framework.datasource.cache.language.model.LanguageEntity

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: LanguageEntity): Long

    @Query("DELETE FROM language WHERE pk = :pk")
    suspend fun deleteLanguage(pk: Int)

    @Query("""
        UPDATE language SET name = :name
        WHERE pk = :pk
        """)
    suspend fun updateLanguage(pk: Int, name: String)

    @Query("""
        SELECT * FROM language
        """)
    suspend fun getAllLanguages(): List<LanguageEntity>
}