package com.example.memo.framework.datasource.cache.search.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memo.framework.datasource.cache.search.model.SearchEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchEntity): Long

    @Query("DELETE FROM search WHERE pk = :pk")
    suspend fun deleteSearch(pk: Int)

    @Query("""
        SELECT * FROM search
        """)
    suspend fun getAllSearches(): List<SearchEntity>

    @Query("""
        UPDATE search SET dismiss = :dismiss
        WHERE pk = :pk
        """)
    suspend fun updateSearchDismiss(pk: Int, dismiss: Int)

    @Query("""
        SELECT * FROM search
        WHERE dismiss <= :acceptedDismiss
        """)
    suspend fun getAcceptedSearchesDismiss(acceptedDismiss: Int): List<SearchEntity>
}