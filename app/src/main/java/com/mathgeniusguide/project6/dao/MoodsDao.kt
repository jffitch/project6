package com.mathgeniusguide.project6.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mathgeniusguide.project6.entity.Moods

@Dao
interface MoodsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(mood: Moods)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMoodIfNotExists(mood: Moods)

    @Update
    fun updateMood(mood: Moods)

    @Delete
    fun deleteMood(mood: Moods)

    @Query("select * from Moods order by time desc limit :limit")
    fun getRecentMoods(limit: Int): LiveData<List<Moods>>

    @Query("select * from Moods order by time desc")
    fun getAllMoods(): LiveData<List<Moods>>

    @Query("select * from Moods order by time desc limit 1")
    fun getOneMood(): Moods

    @Query("select * from Moods where time = :date")
    fun getDateMood(date: String): Moods
}