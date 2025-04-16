package com.github.cawboyroy.expertcoursestudy.load.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

    @Query("SELECT * FROM words where id=:id")
    suspend fun word(id: Int) : WordEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(words: List<WordEntity>)
}