package com.mathgeniusguide.project6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.mathgeniusguide.project6.work.MoodWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdf = SimpleDateFormat("hh:mm:ss")
        val time = sdf.format(Date()).split(":")
        val delay = (87000 - time[0].toInt() * 3600 - time[1].toInt() * 60 - time[2].toInt()).toLong()

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val work = PeriodicWorkRequest.Builder(MoodWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("dailyUpdate", ExistingPeriodicWorkPolicy.REPLACE, work)
    }
}
