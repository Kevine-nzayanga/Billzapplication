package com.kevine.billzapplication

import android.app.Application
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.workmanager.DataSynchWorker
import com.kevine.billzapplication.workmanager.UpcomingBillsWorker
import java.util.concurrent.TimeUnit

class BillzApp:Application() {
    companion object{
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext= applicationContext

        val periodicWorkRequest = PeriodicWorkRequestBuilder<UpcomingBillsWorker>(15, TimeUnit.MINUTES)
            .addTag(Constants.CREATE_UPCOMING_BILLS).build()

        WorkManager
            .getInstance(appContext)
            .enqueueUniquePeriodicWork(Constants.CREATE_UPCOMING_BILLS,
            ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest)

        val constraints= Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val syncPeriodicWorkRequest = PeriodicWorkRequestBuilder<DataSynchWorker>(1,TimeUnit.HOURS)
            .addTag(Constants.SYNC_BILLS).setConstraints(constraints).build()

        WorkManager.getInstance(appContext)
            .enqueueUniquePeriodicWork(Constants.SYNC_BILLS,
                ExistingPeriodicWorkPolicy.KEEP, syncPeriodicWorkRequest)

    }
}