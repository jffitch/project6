package com.mathgeniusguide.project6.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.mathgeniusguide.project6.entity.Moods

@Dao
interface MoodsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMood(mood: Moods)

    @Update
    fun updateMood(mood: Moods)

    @Delete
    fun deleteMood(mood: Moods)

    @Query("select * from Moods order by time desc limit :limit")
    fun getRecentMoods(limit: Int): LiveData<List<Moods>>
}