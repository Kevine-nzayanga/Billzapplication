package com.kevine.billzapplication.repository

import com.kevine.billzapplication.BillzApp
import com.kevine.billzapplication.Database.BillsDb
import com.kevine.billzapplication.model.Bill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BillsRepo {
    var db = BillsDb.getDatabase(BillzApp.appContext)
    val billsDao = db.billsDao()

    suspend fun saveBill(bill:Bill){
        withContext(Dispatchers.IO){
            billsDao.insertBill(bill)
        }
    }
}