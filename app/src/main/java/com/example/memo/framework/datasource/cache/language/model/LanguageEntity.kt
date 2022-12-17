package com.example.memo.framework.datasource.cache.language.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memo.business.domain.models.Language

@Entity(tableName = "language")
class LanguageEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val pk: Int?,

    @ColumnInfo(name = "name")
    val name: String,
)

fun LanguageEntity.toLanguage(): Language {
    return Language(
        pk = pk,
        name = name,
    )
}

fun Language.toEntity(): LanguageEntity{
    return LanguageEntity(
        pk = pk,
        name = name,
    )
}