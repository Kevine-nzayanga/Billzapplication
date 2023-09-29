package com.kevine.billzapplication.api

import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.LoginRequest
import com.kevine.billzapplication.model.LoginResponse
import com.kevine.billzapplication.model.RegisterRequest
import com.kevine.billzapplication.model.RegisterResponse
import com.kevine.billzapplication.model.UpcomingBill
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//we define the request for registering our user
interface ApiInterface {
//    coz of corutine u use a suspend function
    @POST("/users/register")
    suspend fun registerUser(@Body registerRequest:RegisterRequest)
    :Response<RegisterResponse>

    @POST("/users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest)
    :Response<LoginResponse>

    @POST("/bills")
    suspend fun postBill(@Body bill:Bill):Response<Bill>

    @POST("/upcoming-bills")
      suspend fun postUpcomingBill(@Body upcomingBill: UpcomingBill): Response<UpcomingBill>

}