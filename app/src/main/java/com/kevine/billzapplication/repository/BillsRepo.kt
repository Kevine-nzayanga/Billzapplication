package com.kevine.billzapplication.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.kevine.billzapplication.BillzApp
import com.kevine.billzapplication.Database.BillsDb
import com.kevine.billzapplication.api.ApiClient
import com.kevine.billzapplication.api.ApiInterface
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.BillsSummary
import com.kevine.billzapplication.model.UpcomingBill
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID


// thingd in dao come here
class BillsRepo {
    var db = BillsDb.getDatabase(BillzApp.appContext)
    val billsDao = db.billsDao()
    val upcomingBillsDao = db.upcomingBillsDao()
    val apiClient = ApiClient.buildClient(ApiInterface::class.java)



    suspend fun saveBill(bill:Bill){
        withContext(Dispatchers.IO){
            billsDao.insertBill(bill)
        }
    }

    suspend fun insertUpcomingBills(upcomingBill:UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.insertUpcomingBills(upcomingBill)
        }
    }
//    go and querry all the monthly bills go through all of them if there exist an upcoming bill
    suspend fun createRecurringMonthlyBills(){
        withContext(Dispatchers.IO){
               val montlyBills =billsDao.getReccuringBills(Constants.MONTHLY)
            montlyBills.forEach{bill ->
            val cal =Calendar.getInstance()
            val month = cal.get(Calendar.MONTH)+1
            var monthstr= month.toString()
            if (month<10){
                monthstr="0$month"
            }

                if (bill.dueDate.length<2){
                    bill.dueDate = "0${bill.dueDate}"
                }

            val year= cal.get(Calendar.YEAR)
            val startDate = "1/$month/$year"
                val endDate = "31/$month/$year"
                val exisitng = upcomingBillsDao.queryExistingBills(bill.billId, startDate,endDate)
                if (exisitng.isEmpty()){
                    val newUpcomingBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.createDateFromDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    insertUpcomingBills(newUpcomingBill)
                }


            }
        }
    }

    suspend fun createRecurringWeeklyBills(){
        withContext(Dispatchers.IO){
            val weeklyBills= billsDao.getReccuringBills(Constants.WEEKLY)
            val startDate = DateTimeUtils.getFirstDayOfWeek()
            val endDate = DateTimeUtils.getLastDayOfWeek()
            weeklyBills.forEach { bill ->
                val existing =
                    upcomingBillsDao.queryExistingBills(bill.billId, startDate, endDate)
                if (existing.isEmpty()) {
                    val newUpcomingWeeklyBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = DateTimeUtils.getDateOfWeekDay(bill.dueDate),
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    upcomingBillsDao.insertUpcomingBills(newUpcomingWeeklyBill)
                }
            }
        }
    }
    suspend fun createRecurringAnnualBills(){
        withContext(Dispatchers.IO){
            val annualBills= billsDao.getReccuringBills(Constants.YEARLY)
            val year=DateTimeUtils.getCurrentYear()
            val startDate="01/01/$year"
            val endDate= "31/12/$year"
            annualBills.forEach{ bill ->
                val existing=upcomingBillsDao.queryExistingBills(bill.billId,startDate,endDate)
                if (existing.isEmpty()){
                    val newAnnualBill = UpcomingBill(
                        upcomingBillId = UUID.randomUUID().toString(),
                        billId = bill.billId,
                        name = bill.name,
                        amount = bill.amount,
                        frequency = bill.frequency,
                        dueDate = "$year-${bill.dueDate}",
                        userId = bill.userId,
                        paid = false,
                        synced = false
                    )
                    upcomingBillsDao.insertUpcomingBills(newAnnualBill)
                }
            }
        }
    }

    fun getUpcomingBillsByFrequency(frequency:String):LiveData<List<UpcomingBill>>{

        return upcomingBillsDao.getUpcomingBillsByFrequency(frequency, false)
    }

    suspend fun updateUpcomingBill(upcomingBill: UpcomingBill){
        withContext(Dispatchers.IO){
            upcomingBillsDao.updateUpcomingBill(upcomingBill)
        }
    }

    fun getPaidBills():LiveData<List<UpcomingBill>>{
        return upcomingBillsDao.getPaidBills()
    }


    fun getAccessToken():String{
        val prefs = BillzApp.appContext.getSharedPreferences(Constants.PREFS,Context.MODE_PRIVATE)
        var token = prefs.getString(Constants.ACCESS_TOKEN, "")?:""
        token = "Bearer $token"
        return token
    }

    suspend fun syncBills() {
        withContext(Dispatchers.IO){
            val accessToken = getAccessToken()
            val unsyncedBills=billsDao.getUnsyncedBills()
            unsyncedBills.forEach {bill->
            val response= apiClient.postBill(accessToken,bill)
            if (response.isSuccessful){
                bill.synced=true
                billsDao.insertBill(bill)
            }

            }
        }
    }

    suspend fun syncUpcomingBills() {
        withContext(Dispatchers.IO){
            val accessToken = getAccessToken()
            upcomingBillsDao.getUnsyncedUpcomingBills().forEach { upcomingBill ->
                val response=apiClient.postUpcomingBill(accessToken, upcomingBill)
                if (response.isSuccessful){
                    upcomingBill.synced=true
                    upcomingBillsDao.updateUpcomingBill(upcomingBill)
                }

            }
        }
    }

    suspend fun fetchRemoteBills(){
        withContext(Dispatchers.IO){
            val token = getAccessToken()
            val response = apiClient.fetchRemoteBills(token)
            if (response.isSuccessful) {
                response.body()?.forEach { bill ->
                    billsDao.insertBill(bill)

                }
            }
        }
    }

    suspend fun fetchUpcomingRemoteBills(){
        withContext(Dispatchers.IO){
            val token = getAccessToken()
            val response = apiClient.fetchUpcomingBills(token)
            if (response.isSuccessful) {
                response.body()?.forEach { upcomingBill ->
                    upcomingBillsDao.insertUpcomingBills(upcomingBill)

                }
            }
        }
    }

    suspend fun getMonthlySummary():BillsSummary {
        return withContext(Dispatchers.IO){
            val startDate = DateTimeUtils.getFirstDayOfMonth()
            val endDate = DateTimeUtils.getLastDayOfMonth()
            val today = DateTimeUtils.getCurrentDate()

            val paid = upcomingBillsDao.getTotalPaidMonthly(startDate,endDate)
            val upcoming = upcomingBillsDao.getUpcomingAmountThisMonth(startDate,endDate,today)
            val overdue= upcomingBillsDao.getOverdueAmountThisMonth(startDate,endDate,today)
            val total = upcomingBillsDao.getTotalMonthlyAmount(startDate,endDate)

            val summary = BillsSummary(paid= paid, upcoming =upcoming, overdue=overdue, total=total)

            summary

        }
    }
}