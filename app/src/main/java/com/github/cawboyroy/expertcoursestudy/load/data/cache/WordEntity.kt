package com.github.cawboyroy.expertcoursestudy.load.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("value")
    val text: String,
)