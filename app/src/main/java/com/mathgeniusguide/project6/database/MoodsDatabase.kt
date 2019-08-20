package com.mathgeniusguide.project6.database

import android.content.Context
import androidx.room.*
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.entity.Moods

@Database(entities = [Moods::class], version = 1, exportSchema = false)
abstract class MoodsDatabase : RoomDatabase() {
    abstract fun moodsDao(): MoodsDao

    companion object {
        private var INSTANCE: MoodsDatabase? = null
        var TEST_MODE = false

        fun getDataBase(context: Context): MoodsDatabase? {
            if (INSTANCE == null) {
                synchronized(MoodsDatabase::class) {
                    if (TEST_MODE) {
                        INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, MoodsDatabase::class.java)
                            .allowMainThreadQueries()
                            .build()
                    } else {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, MoodsDatabase::class.java, "myDB")
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