package com.kevine.billzapplication.repository

import com.kevine.billzapplication.BillzApp
import com.kevine.billzapplication.Database.BillsDb
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.UpcomingBill
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.utils.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID

class BillsRepo {
    var db = BillsDb.getDatabase(BillzApp.appContext)
    val billsDao = db.billsDao()
    val upcomingBillsDao = db.upcomingBillsDao()

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
                        dueDate = "${bill.dueDate} / $month/$year",
                        userId = bill.userId,
                        paid = false
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
                        paid = false
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
                        dueDate = "${bill.dueDate}/$year",
                        userId = bill.userId,
                        paid = false
                    )
                    upcomingBillsDao.insertUpcomingBills(newAnnualBill)
                }
            }
        }
    }

}