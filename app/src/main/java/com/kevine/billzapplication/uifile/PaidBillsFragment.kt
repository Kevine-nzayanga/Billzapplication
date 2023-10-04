package com.kevine.billzapplication.uifile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.FragmentPaidBillsBinding
import com.kevine.billzapplication.model.UpcomingBill
import com.kevine.billzapplication.viewmodel.BillsViewModel


class PaidBillsFragment : Fragment(),OnClickBill {
     var binding: FragmentPaidBillsBinding? = null
    val billsViewModel:BillsViewModel by  viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaidBillsBinding.inflate(layoutInflater,container,false)
        return binding?.root

        // Inflate the layout for this fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        billsViewModel.getPaidBills().observe(this){paidBills->
            val adapter = UpcomingBillsAdapter(paidBills,this)
            binding?.rvpaid?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvpaid?.adapter = adapter
        }
    }

    override fun checkPaidBill(upcomingBill: UpcomingBill) {
        upcomingBill.paid = !upcomingBill.paid
        !upcomingBill.synced
        billsViewModel.updateUpcomingBill(upcomingBill)
    }


}