package com.example.memo.framework.datasource.datastore


interface AppDataStoreService {

    suspend fun setValue(
        key: String,
        value: String
    )

    suspend fun readValue(
        key: String,
    ): String?
}