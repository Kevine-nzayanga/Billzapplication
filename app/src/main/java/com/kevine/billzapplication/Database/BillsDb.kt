package com.kevine.billzapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.UpcomingBill

//we make it abstract so that we dont implement the many funs in room
//we add a companion since due to it being abstract we cant
//instatiate it
@Database (entities = [Bill::class, UpcomingBill::class], version = 2)
abstract class BillsDb:RoomDatabase() {
abstract fun billsDao():BillsDao
abstract fun upcomingBillsDao(): UpcomingBillsDao
companion object{
    var database:BillsDb?= null

    fun getDatabase(context:Context):BillsDb{
        if (database == null){
            database = Room
                .databaseBuilder(context, BillsDb::class.java,"BillzDb")
                .fallbackToDestructiveMigration()
                .build()

        }
        return database as BillsDb
//        the as is to coerxe the non nullable db to a nullable db

    }
}

}
