package com.kevine.billzapplication.uifile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevine.billzapplication.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
lateinit var binding:FragmentSummaryBinding
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
    }



}
