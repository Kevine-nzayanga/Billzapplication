package com.kevine.billzapplication.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.activity.viewModels
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.ActivityMainBinding
import com.kevine.billzapplication.model.RegisterRequest
import com.kevine.billzapplication.utils.Constants
import com.kevine.billzapplication.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val userViewModel:UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onResume()
        redirectUser()

    }

    override fun onResume() {
        super.onResume()
        clearErrorOnType()

        binding.btnsignup.setOnClickListener {
            validateRegisterContacts()
        }
        binding.btnlogin.setOnClickListener {
            val intent = Intent(this@MainActivity, Loginactivity::class.java)
            startActivity(intent)

        }


        userViewModel.regLiveData.observe(this) { regResponse ->
            Toast.makeText(this, regResponse.message, Toast.LENGTH_LONG).show()
            startActivity(Intent(this@MainActivity,Loginactivity::class.java))
            binding.proRegister.visibility = View.VISIBLE

        }

        userViewModel.errLiveData.observe(this) { err ->
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()
            binding.proRegister.visibility= View.VISIBLE
        }


    }
    fun clearErrorOnType() {

        binding.etfirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlfirstName.error = null

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlfirstName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.etsecondName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlsecondName.error = null

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlsecondName.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.etnum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlnum.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlnum.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.etemail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlemail.error = null

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlemail.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.etpassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tlpassowrd.error = null

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tlpassowrd.error = null
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })



    }

    fun validateRegisterContacts() {
        val firstName = binding.etfirstName.text.toString()
        val lastName = binding.etsecondName.text.toString()
        val phoneNumber = binding.etnum.text.toString()
        val email = binding.etemail.text.toString()
        val password = binding.etpassword.text.toString()
        val passwordComfirm = binding.etpasswordcomfirm.text.toString()


        var error = false

        if (firstName.isBlank()) {
            binding.tlfirstName.error = "First name is required"
            error = true
        }
        if (lastName.isBlank()) {
            binding.tlfirstName.error = "Last name is required"
            error = true
        }

        if (phoneNumber.isBlank()) {
            binding.tlnum.error = "contacts is required"
            error = true
        }

        if (email.isBlank()) {
            binding.tlemail.error = "Email is required"
            error = true
        }
        if (email.isBlank()) {
            binding.tlemail.error = "Email is required"
            error = true
        }
        if (password.isBlank()) {
            binding.tlpassowrd.error = "Email is required"
            error = true
        }

            if (password!=passwordComfirm){
                binding.tlpassowrdcomfirm.error = "password don`t match password comfirmation is required"
                error = true
            }


//            not a must to follow order
            if (!error){
                val registerRequest= RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber= phoneNumber,
                    email = email,
                    password = password

                )
                userViewModel.registerUser(registerRequest)
            }
//

}
    fun redirectUser(){
        val sharedPrefs = getSharedPreferences(Constants.PREFS,Context.MODE_PRIVATE)
        val userId = sharedPrefs.getString(Constants.USER_ID,Constants.EMPTY_STRING)?:Constants.EMPTY_STRING
        if (userId.isNotBlank()){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, Loginactivity::class.java))
        }

    }
}






