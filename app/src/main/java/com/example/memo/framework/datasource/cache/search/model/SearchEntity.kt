package com.example.memo.framework.datasource.cache.search.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memo.business.domain.models.Search

@Entity(tableName = "search")
class SearchEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val pk: Int?,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "language_source")
    val languageSource: String,

    @ColumnInfo(name = "language_destination")
    val languageDestination: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "dismiss")
    val dismiss: Int,
)

fun SearchEntity.toSearch(): Search {
    return Search(
        pk = pk,
        word = word,
        languageSource = languageSource,
        languageDestination = languageDestination,
        url = url,
        dismiss = dismiss
    )
}

fun Search.toEntity(): SearchEntity {
    return SearchEntity(
        pk = pk,
        word = word,
        languageSource = languageSource,
        languageDestination = languageDestination,
        url = url,
        dismiss = dismiss
    )
}