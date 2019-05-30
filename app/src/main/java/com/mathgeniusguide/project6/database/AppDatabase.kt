package com.mathgeniusguide.project6.database

import android.content.Context
import android.arch.persistence.room.*
import com.mathgeniusguide.project6.converter.DateTypeConverter
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.entity.Moods

@Database(entities = [Moods::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodsDao() : MoodsDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}