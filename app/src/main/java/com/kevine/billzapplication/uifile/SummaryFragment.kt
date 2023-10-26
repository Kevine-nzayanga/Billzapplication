package com.kevine.billzapplication.uifile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.graphics.vector.SolidFill
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.FragmentSummaryBinding
import com.kevine.billzapplication.model.BillsSummary
import com.kevine.billzapplication.utils.Utils
import com.kevine.billzapplication.viewmodel.BillsViewModel

class SummaryFragment : Fragment() {
lateinit var binding:FragmentSummaryBinding
val billsViewModel:BillsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.fabtnsummary.setOnClickListener{
            startActivity(Intent(requireContext(), AddBillActivity::class.java))
        }
        billsViewModel.getMonthlySummary()
        showMonthlySummary()

        }

    fun showMonthlySummary(){
        billsViewModel.summaryLiveData.observe(this){summary->
            binding?.tvPaidAmt?.text=Utils.formatCurrency(summary.paid)
            binding?.tvOverdueAmt?.text=Utils.formatCurrency(summary.overdue)
            binding?.tvUpcomingAmt?.text=Utils.formatCurrency(summary.upcoming)
            binding?.tvTotalAmt?.text=Utils.formatCurrency(summary.total)
            ShowChart(summary)
        }
    }
    fun ShowChart(summary:BillsSummary){
        val entries = mutableListOf<DataEntry>()
        entries.add(ValueDataEntry("Paid",summary.paid))
        entries.add(ValueDataEntry("Upcoming",summary.upcoming))
        entries.add(ValueDataEntry("Overdue",summary.overdue))

        val pieChart = AnyChart.pie()
        pieChart.data(entries)
        pieChart.innerRadius(80)
        pieChart.palette().itemAt(0, SolidFill("#4CAF50",100))
        pieChart.palette().itemAt(1, SolidFill("#FF00FF",100))
        pieChart.palette().itemAt(2, SolidFill("#00FFFF",100))
        binding?.summaryChart?.setChart(pieChart)
    }

//    fun displayPieChart(summary: BillsSummary) {
//        binding?.pieChart?.apply{
//            setDataPoints(floatArrayOf(summary.paid.toFloat(),summary.upcoming.toFloat(),summary.overdue.toFloat()))
//            setCenterColor(android.R.color.white)
//            setSliceColor(intArrayOf(R.color.green))
//        }
//    }
//
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
    }




}
