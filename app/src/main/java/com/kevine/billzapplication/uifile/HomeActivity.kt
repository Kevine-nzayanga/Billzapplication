package com.kevine.billzapplication.uifile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.ActivityHomeBinding
import com.kevine.billzapplication.uifile.PaidBillsFragment
import com.kevine.billzapplication.uifile.SettingsFragment
import com.kevine.billzapplication.uifile.SummaryFragment
import com.kevine.billzapplication.uifile.UpcomingBillsFragment
import com.kevine.billzapplication.viewmodel.BillsViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val billsViewModel:BillsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onResume()
    }

    override fun onResume() {
        super.onResume()
        setupBottomNav()
        billsViewModel.createUpcomingBills()

    }

    fun setupBottomNav(){
        binding.bnvhome.setOnItemSelectedListener { menuItem ->
       when(menuItem.itemId) {
           R.id.summary -> {
               supportFragmentManager
                   .beginTransaction()
                   .replace(R.id.fvchome, SummaryFragment())
                   .commit()

               true
           }

           R.id.upcoming -> {
                       supportFragmentManager
                           .beginTransaction()
                           .replace(R.id.fvchome, UpcomingBillsFragment())
                           .commit()
               true
           }

           R.id.paid -> {
               supportFragmentManager
                   .beginTransaction()
                   .replace(R.id.fvchome, PaidBillsFragment())
                   .commit()
               true
           }

           R.id.settings -> {
               supportFragmentManager
                   .beginTransaction()
                   .replace(R.id.fvchome, SettingsFragment())
                   .commit()
               true
           }
           else -> false

       }
        }
    }
}