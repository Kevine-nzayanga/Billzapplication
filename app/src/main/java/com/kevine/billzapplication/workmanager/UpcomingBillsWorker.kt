package com.kevine.billzapplication.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kevine.billzapplication.repository.BillsRepo

class UpcomingBillsWorker (context: Context, workerParameters: WorkerParameters):
    CoroutineWorker(context,workerParameters) {
    val billsRepository= BillsRepo()
    override suspend fun doWork(): Result {
        billsRepository.createRecurringWeeklyBills()
        billsRepository.createRecurringMonthlyBills()
        billsRepository.createRecurringAnnualBills()
        return Result.success()

    }
}