package com.github.cawboyroy.expertcoursestudy

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordEntity
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsDao
import com.github.cawboyroy.expertcoursestudy.load.data.cache.WordsDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: WordsDao
    private lateinit var database: WordsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WordsDatabase::class.java
        ).build()
        dao = database.dao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun test() = runBlocking {
        dao.save(listOf(WordEntity(0, "0"), WordEntity(1, "1")))

        TestCase.assertEquals(WordEntity(0, "0"), dao.word(0))
        TestCase.assertEquals(WordEntity(1, "1"), dao.word(1))

        dao.save(listOf(WordEntity(0, "zero"), WordEntity(1, "one")))

        TestCase.assertEquals(WordEntity(0, "zero"), dao.word(0))
        TestCase.assertEquals(WordEntity(1, "one"), dao.word(1))
    }
}