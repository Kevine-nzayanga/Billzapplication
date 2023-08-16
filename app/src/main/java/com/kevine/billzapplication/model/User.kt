package com.kevine.billzapplication.model

import com.google.gson.annotations.SerializedName

data class User(
   @SerializedName("phone_number") var phoneNumber:String,
   @SerializedName("first_name")   var firstNmae:String,
   @SerializedName("last_name")  var lastName:String,
   @SerializedName("user_id")   var userId:String,
   var password:String,
    var email:String
)
