package com.example.memo.business.datasource.datasource

interface AppDataStoreSource {

    suspend fun setValue(
        key: String,
        value: String
    )

    suspend fun readValue(
        key: String,
    ): String?
}