package com.kevine.billzapplication.workmanager

import android.content.Context
import android.content.ContextParams
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kevine.billzapplication.repository.BillsRepo

class DataSynchWorker(context: Context, workerParams: WorkerParameters):
CoroutineWorker(context,workerParams){
    val billsRepo= BillsRepo()
    override suspend fun doWork(): Result {
        billsRepo.syncBills()
        billsRepo.syncUpcomingBills()
        return Result.success()
    }
}