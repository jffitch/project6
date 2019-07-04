package com.mathgeniusguide.project6.database

import android.content.Context
import androidx.room.*
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.entity.Moods

@Database(entities = [Moods::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodsDao(): MoodsDao

    companion object {
        var TEST_MODE = false
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (TEST_MODE) {
                        INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                            .allowMainThreadQueries()
                            .build()
                    } else {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB")
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}