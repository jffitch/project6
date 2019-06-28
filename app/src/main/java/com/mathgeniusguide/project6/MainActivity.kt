package com.mathgeniusguide.project6

import android.os.Bundle
import android.widget.Toast
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

        var sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = sdf.format(Date())
        sdf = SimpleDateFormat("HH:mm:ss")
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
        WorkManager.getInstance(this)
            .enqueueUniqueWork(date, ExistingWorkPolicy.KEEP, work)
    }
}
