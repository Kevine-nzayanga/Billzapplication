package com.kevine.billzapplication.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.temporal.TemporalAmount

@Entity(tableName = "Bills",
    indices = [Index(value = ["name"], unique = true)])
data class Bill(
  @SerializedName("bill_id")  @PrimaryKey val billId:String,
    var name:String,
    var amount:Double,
    var frequency:String,
    @SerializedName("due_date") var dueDate:String,
   @SerializedName("user_id") var userId:String,
   @Expose(serialize = false) var synced:Boolean
)
