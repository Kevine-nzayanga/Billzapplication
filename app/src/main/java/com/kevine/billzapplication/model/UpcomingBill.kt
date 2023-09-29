package com.kevine.billzapplication.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UpcomingBills",
    indices = [Index(value = ["billId","dueDate"], unique = true)])
data class UpcomingBill(
   @SerializedName("upcoming_bill_id") @PrimaryKey var upcomingBillId:String,
  @SerializedName("bill_id")  var billId:String,
    var name:String,
    var amount:Double,
    var frequency:String,
  @SerializedName("due_date")  var dueDate:String,
   @SerializedName("user_id") var userId:String,
    var paid:Boolean,
   @Expose(serialize = false) var synced:Boolean
)
