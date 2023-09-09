package com.kevine.billzapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UpcomingBills")
data class UpcomingBill(
    @PrimaryKey var upcomingBillId:String,
    var billId:String,
    var name:String,
    var amount:Double,
    var frequency:String,
    var dueDate:String,
    var userId:String,
    var paid:Boolean
)
