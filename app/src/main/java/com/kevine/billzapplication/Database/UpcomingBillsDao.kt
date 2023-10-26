package com.kevine.billzapplication.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kevine.billzapplication.model.UpcomingBill

@Dao
interface UpcomingBillsDao {
//    wespecify a uniqueness constraint like upcoming bill is inserted like once monthly when its expected
//  allows bulk insert of upcoming bills

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUpcomingBills( vararg upcomingBill: UpcomingBill)

    @Query("SELECT * FROM UpcomingBills WHERE billId =:billId AND dueDate BETWEEN :startDate AND :endDate LIMIT 1")
    fun queryExistingBills(billId:String, startDate:String, endDate:String):List<UpcomingBill>

    @Query ("SELECT* FROM UpcomingBills WHERE frequency = :freq AND paid = :paid ORDER BY dueDate")
   fun getUpcomingBillsByFrequency(freq:String, paid:Boolean):LiveData<List<UpcomingBill>>

   @Update(onConflict = OnConflictStrategy.REPLACE)
   fun updateUpcomingBill(upcomingBill: UpcomingBill)

   @Query("SELECT*FROM UpcomingBills WHERE paid= :paid ORDER BY dueDate")
   fun getPaidBills(paid: Boolean=true):LiveData<List<UpcomingBill>>

    @Query("SELECT * FROM UpcomingBills WHERE synced = 0")
    fun getUnsyncedUpcomingBills(): List<UpcomingBill>

    @Query("SELECT SUM (amount) FROM UpcomingBills WHERE dueDate BETWEEN  :startDate AND :endDate")
    fun getTotalMonthlyAmount(startDate:String, endDate:String):Double

    @Query("SELECT SUM (amount) FROM UpcomingBills WHERE paid=1 AND dueDate BETWEEN :startDate AND :endDate" )
    fun getTotalPaidMonthly(startDate: String, endDate: String):Double

    @Query("SELECT SUM (amount) FROM UpcomingBills WHERE paid=0 AND dueDate > :today AND dueDate BETWEEN :startDate AND :endDate")
    fun getUpcomingAmountThisMonth(startDate: String, endDate: String, today:String):Double

    @Query("SELECT SUM (amount) FROM UpcomingBills WHERE paid=0 AND dueDate < :today AND dueDate BETWEEN :startDate AND :endDate")
    fun getOverdueAmountThisMonth(startDate: String, endDate: String, today:String):Double

}


