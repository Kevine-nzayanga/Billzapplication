package com.kevine.billzapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse (
  @Expose  var message:String,
  @Expose  @SerializedName("access_token")var accessToken:String,
  @Expose @SerializedName("user_id") var userId:String
)
