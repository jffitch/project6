package com.mathgeniusguide.project6.work

import android.content.Context
import androidx.work.*
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

        val oneMood = db?.moodsDao()?.getOneMood()
        var sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val date = sdf.format(Date())

        with(moodsDao) {
            this?.insertMoodIfNotExists(Moods(date, oneMood!!.emotion, applicationContext.getString(R.string.carried_over)))
        }

        sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val time = sdf.format(Date()).split(":")
        val SECONDS_IN_MINUTE = 60
        val SECONDS_IN_HOUR = 3600
        val SECONDS_IN_DAY = 86400
        val delay =
            (SECONDS_IN_DAY + 10 * SECONDS_IN_MINUTE - time[0].toInt() * SECONDS_IN_HOUR - time[1].toInt() * SECONDS_IN_MINUTE - time[2].toInt()).toLong()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val work = OneTimeWorkRequestBuilder<MoodWorker>()
            .setConstraints(constraints)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(date, ExistingWorkPolicy.KEEP, work)
        return Result.success()
    }
}