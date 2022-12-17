package com.example.memo.framework.datasource.cache.dictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo.framework.datasource.cache.dictionary.model.DictionaryEntity

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionary: DictionaryEntity): Long

    @Query("DELETE FROM dictionary WHERE pk = :pk")
    suspend fun deleteDictionary(pk: Int)

    @Query("""
        SELECT * FROM dictionary
        """)
    suspend fun getAllDictionaries(): List<DictionaryEntity>
}