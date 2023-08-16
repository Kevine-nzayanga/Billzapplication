package com.kevine.billzapplication.repository

import com.kevine.billzapplication.api.ApiClient
import com.kevine.billzapplication.api.ApiInterface
import com.kevine.billzapplication.model.LoginRequest
import com.kevine.billzapplication.model.LoginResponse
import com.kevine.billzapplication.model.RegisterRequest
import com.kevine.billzapplication.model.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository {
    val apiClient= ApiClient.buildClient(ApiInterface::class.java)
//    swith the content to the io
    suspend fun register(registerRequest: RegisterRequest):
            Response<RegisterResponse>{
        return withContext(Dispatchers.IO){
    apiClient.registerUser(registerRequest)
    }}


//    newcode
    suspend fun login(loginRequest: LoginRequest):
            Response<LoginResponse>{
       return withContext(Dispatchers.IO){
           apiClient.loginUser(loginRequest)
       }
    }
}