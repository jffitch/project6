package com.mathgeniusguide.project6.database

import androidx.test.InstrumentationRegistry
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.entity.Moods
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoodsDatabaseTest {
    private var moodsDao: MoodsDao? = null

    @Before
    fun setup() {
        MoodsDatabase.TEST_MODE = true
        moodsDao = MoodsDatabase.getDataBase(InstrumentationRegistry.getTargetContext())?.moodsDao()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun insert_replace() {
        val mood = Moods("1900/01/01", 5, "test")
        moodsDao?.insertMood(mood)
        val moodTest = moodsDao?.getDateMood("1900/01/01")
        Assert.assertEquals(moodTest?.emotion, 5)
    }

    @Test
    fun insert_replace_replaced() {
        val mood1 = Moods("1900/01/02", 5, "test")
        moodsDao?.insertMood(mood1)
        val mood2 = Moods("1900/01/02", 4, "test")
        moodsDao?.insertMood(mood2)
        val moodTest = moodsDao?.getDateMood("1900/01/02")
        Assert.assertEquals(moodTest?.emotion, 4)
    }

    @Test
    fun insert_replace_kept() {
        val mood1 = Moods("1900/01/03", 5, "test")
        moodsDao?.insertMood(mood1)
        val mood2 = Moods("1900/01/03", 4, "test")
        moodsDao?.insertMood(mood2)
        val moodTest = moodsDao?.getDateMood("1900/01/03")
        Assert.assertEquals(moodTest?.emotion, 5)
    }

    @Test
    fun insert_keep() {
        val mood = Moods("1900/01/04", 5, "test")
        moodsDao?.insertMoodIfNotExists(mood)
        val moodTest = moodsDao?.getDateMood("1900/01/04")
        Assert.assertEquals(moodTest?.emotion, 5)
    }

    @Test
    fun insert_keep_replaced() {
        val mood1 = Moods("1900/01/05", 5, "test")
        moodsDao?.insertMoodIfNotExists(mood1)
        val mood2 = Moods("1900/01/05", 4, "test")
        moodsDao?.insertMoodIfNotExists(mood2)
        val moodTest = moodsDao?.getDateMood("1900/01/05")
        Assert.assertEquals(moodTest?.emotion, 4)
    }

    @Test
    fun insert_keep_kept() {
        val mood1 = Moods("1900/01/06", 5, "test")
        moodsDao?.insertMoodIfNotExists(mood1)
        val mood2 = Moods("1900/01/06", 4, "test")
        moodsDao?.insertMoodIfNotExists(mood2)
        val moodTest = moodsDao?.getDateMood("1900/01/06")
        Assert.assertEquals(moodTest?.emotion, 5)
    }
}