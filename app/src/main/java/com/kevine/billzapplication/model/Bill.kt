package com.kevine.billzapplication.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount

@Entity(tableName = "Bills",
    indices = [Index(value = ["name"], unique = true)])
data class Bill(
    @PrimaryKey val billId:String,
    var name:String,
    var amount:Double,
    var frequency:String,
    var dueDate:String,
    var userId:String
)
