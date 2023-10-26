package com.kevine.billzapplication.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kevine.billzapplication.model.Bill

@Dao
interface BillsDao {
//    if there is a conflict btn this and the one being added dao should replace it
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBill(bill:Bill)

    @Query("SELECT * FROM Bills WHERE frequency= :freq")
    fun getReccuringBills(freq: String):List<Bill>

    @Query ("SELECT* FROM Bills WHERE synced=0")
    fun getUnsyncedBills(): List<Bill>





}