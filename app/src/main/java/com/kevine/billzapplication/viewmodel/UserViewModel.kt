package com.kevine.billzapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevine.billzapplication.model.LoginRequest
import com.kevine.billzapplication.model.LoginResponse
import com.kevine.billzapplication.model.RegisterRequest
import com.kevine.billzapplication.model.RegisterResponse
import com.kevine.billzapplication.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
//instatiate
    val userRepo = UserRepository()
    val regLiveData=MutableLiveData<RegisterResponse>()
    val regLiveDataLogin = MutableLiveData<LoginResponse>()
    val errLiveData = MutableLiveData<String>()

//    u cant invoke suspend outside coroutine
    fun registerUser(registerRequest:RegisterRequest){

//    populate with our live data otherwise we put an error
        viewModelScope.launch {
            val response= userRepo.register(registerRequest)
            if (response.isSuccessful){
                regLiveData.postValue(response.body())
            }
            else{
                errLiveData.postValue(response.errorBody()?.string())
            }
        }
    }
    fun loginUser(loginRequest: LoginRequest){
        viewModelScope.launch {
            val responselogin= userRepo.login(loginRequest)
            if (responselogin.isSuccessful){
                regLiveDataLogin.postValue(responselogin.body())
            }
            else{
                errLiveData.postValue(responselogin.errorBody()?.string())
            }
        }
    }


}