package com.mathgeniusguide.project6.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mathgeniusguide.project6.R
import com.mathgeniusguide.project6.dao.MoodsDao
import com.mathgeniusguide.project6.database.AppDatabase
import com.mathgeniusguide.project6.entity.Moods
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MoodWorker(context: Context, workerParams: WorkerParameters) : Worker (context, workerParams) {
    private var db: AppDatabase? = null
    private var moodsDao: MoodsDao? = null

    override fun doWork() : Result {
        db = AppDatabase.getAppDataBase(applicationContext)
        moodsDao = db?.moodsDao()

        var oneMood = db?.moodsDao()?.getOneMood()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = sdf.format(Date())

        with(moodsDao) {
            this?.insertMoodIfNotExists(Moods(date, oneMood!!.emotion, applicationContext.getString(R.string.carried_over)))
        }
        return Result.success()
    }
}