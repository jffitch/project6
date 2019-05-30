package com.mathgeniusguide.project6

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.AppDatabase
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.fromCallable({
            db = AppDatabase.getAppDataBase(context = this)
            moodsDao = db?.moodsDao()
        })
    }
}
