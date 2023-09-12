package com.kevine.billzapplication.uifile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.FragmentUpcomingBillsBinding
import com.kevine.billzapplication.viewmodel.BillsViewModel


class UpcomingBillsFragment : Fragment() {
var binding:FragmentUpcomingBillsBinding?=null
    val billsViewHolder:BillsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpcomingBillsBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        billsViewHolder.getWeeklyUpcoming().observe(this){upcomingWeekly->
            val adapter = UpcomingBillsAdapter(upcomingWeekly)
            binding?.rvweekly?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvweekly?.adapter= adapter

        }

        billsViewHolder.getMonthlyUpcoming().observe(this){upcomingMonthly->
            val adapter = UpcomingBillsAdapter(upcomingMonthly)
            binding?.rvmontly?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvmontly?.adapter= adapter

        }

        billsViewHolder.getAnnualUpcoming().observe(this){upcomingAnnual->
            val adapter = UpcomingBillsAdapter(upcomingAnnual)
            binding?.rvannual?.layoutManager = LinearLayoutManager(requireContext())
            binding?.rvannual?.adapter= adapter

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}