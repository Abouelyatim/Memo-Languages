package com.example.memo.framework.datasource.cache.dictionary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memo.business.domain.models.Dictionary

@Entity(tableName = "dictionary")
class DictionaryEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val pk: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "url")
    val url: String,
)

fun DictionaryEntity.toDictionary(): Dictionary {
    return Dictionary(
        pk = pk,
        name = name,
        url = url
    )
}

fun Dictionary.toEntity(): DictionaryEntity {
    return DictionaryEntity(
        pk = pk,
        name = name,
        url = url
    )
}