package com.github.cawboyroy.expertcoursestudy.load.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {

    abstract fun dao(): WordsDao
}