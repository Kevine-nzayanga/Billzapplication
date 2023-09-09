package com.kevine.billzapplication.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kevine.billzapplication.model.UpcomingBill

@Dao
interface UpcomingBillsDao {
//    wespecify a uniqueness constraint like upcoming bill is inserted like once monthly when its expected
//  allows bulk insert of upcoming bills

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUpcomingBills( vararg upcomingBill: UpcomingBill)

    @Query("SELECT * FROM UpcomingBills WHERE billId =:billId AND dueDate BETWEEN :startDate AND :endDate LIMIT 1")
    fun queryExistingBills(billId:String, startDate:String, endDate:String):List<UpcomingBill>

}