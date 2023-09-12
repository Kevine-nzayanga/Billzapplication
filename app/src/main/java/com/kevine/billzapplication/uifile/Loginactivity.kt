package com.kevine.billzapplication.uifile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import com.kevine.billzapplication.databinding.ActivityLoginactivityBinding
import com.kevine.billzapplication.model.LoginRequest
import com.kevine.billzapplication.model.LoginResponse
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.viewmodel.UserViewModel

class Loginactivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginactivityBinding
    val userViewModel:UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onResume()

    }

    override fun onResume() {
        super.onResume()
        clearErrorOnType()

        binding.btnlogin2.setOnClickListener {
            validateLoginUser()

        }
        binding.btnsignup2.setOnClickListener {
            val intent = Intent(this@Loginactivity, MainActivity::class.java)
            startActivity(intent)
        }

       initobserver()

    }


    fun clearErrorOnType() {
        binding.etemail2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlemail2.error = null

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlemail2.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.etpassword2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlpassword2.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

        fun validateLoginUser() {
            val email = binding.etemail2.text.toString()
            val password = binding.etpassword2.text.toString()

            var error = false
            if (email.isBlank()) {
                binding.tlemail2.error = "name is required"
                error = true
            }

            if (password.isBlank()) {
                binding.tlpassword2.error = "Email is required"
                error = true
            }


            if(!error){
                val loginRequest = LoginRequest(
                    email = email,
                    password = password
                )
                userViewModel.loginUser(loginRequest)
            }


        }

    fun initobserver() {
        userViewModel.regLiveDataLogin.observe(this) { loginResponse ->
            persistlogin(loginResponse)
            //            binding.pblogin.visibility = View.GONe
            Toast.makeText(this, loginResponse.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this@Loginactivity, HomeActivity::class.java))
        }


        userViewModel.errLiveData.observe(this) { err ->
//            binding.pblogin.visibility = View.GONE
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()

        }
    }

//    this is so that the user doesnt keep login in and do the apply so that the changes are saved
    fun persistlogin(loginResponse: LoginResponse){
        val sharedPrefs = getSharedPreferences(Constants.PREFS,Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        editor.putString(Constants.USER_ID, loginResponse.userId)
        editor.putString(Constants.ACCESS_TOKEN,loginResponse.accessToken)
        editor.apply()
    }

    }
