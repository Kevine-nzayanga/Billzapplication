package com.kevine.billzapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.repository.BillsRepo
import kotlinx.coroutines.launch

class BillsViewModel:ViewModel() {
    val billsRepo = BillsRepo()

    fun saveBill(bill: Bill){
        viewModelScope.launch {
            billsRepo.saveBill(bill)
        }

    }

    fun createUpcomingBills(){
        viewModelScope.launch {
            billsRepo.createRecurringWeeklyBills()
            billsRepo.createRecurringMonthlyBills()
        }
    }
}