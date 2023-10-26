package com.kevine.billzapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevine.billzapplication.model.Bill
import com.kevine.billzapplication.model.BillsSummary
import com.kevine.billzapplication.model.UpcomingBill
import com.kevine.billzapplication.repository.BillsRepo
import com.kevine.billzapplication.utils.Constants
import kotlinx.coroutines.launch

class BillsViewModel:ViewModel() {
    val billsRepo = BillsRepo()
    val summaryLiveData= MutableLiveData<BillsSummary>()

    fun saveBill(bill: Bill) {
        viewModelScope.launch {
            billsRepo.saveBill(bill)
        }

    }

    fun createUpcomingBills(){
        viewModelScope.launch {
            billsRepo.createRecurringWeeklyBills()
            billsRepo.createRecurringMonthlyBills()
            billsRepo.createRecurringAnnualBills()
        }
    }

    fun getWeeklyUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.WEEKLY)
    }


    fun getMonthlyUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.MONTHLY)
    }

    fun getAnnualUpcoming(): LiveData<List<UpcomingBill>>{
        return billsRepo.getUpcomingBillsByFrequency(Constants.YEARLY)
    }

    fun updateUpcomingBill(upcomingBill: UpcomingBill) {
        viewModelScope.launch {
            billsRepo.updateUpcomingBill(upcomingBill)
        }
    }

    fun getPaidBills():LiveData<List<UpcomingBill>>{
        return billsRepo.getPaidBills()
    }

//    we launch coroutine when launching suspend functions
    fun downloadRemoteData(){
        viewModelScope.launch {
            billsRepo.fetchRemoteBills()
            billsRepo.fetchUpcomingRemoteBills()
        }

    }

    fun getMonthlySummary(){
        viewModelScope.launch {
            summaryLiveData.postValue(billsRepo.getMonthlySummary())
        }

    }

    }