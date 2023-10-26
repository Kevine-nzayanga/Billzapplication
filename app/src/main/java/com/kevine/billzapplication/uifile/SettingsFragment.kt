package com.kevine.billzapplication.uifile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.BuildConfig
import com.kevine.billzapplication.R
import com.kevine.billzapplication.databinding.FragmentSettingsBinding
import com.kevine.billzapplication.databinding.FragmentSummaryBinding
import com.kevine.billzapplication.utils.Constants

class SettingsFragment : Fragment() {
    var binding :FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        binding?.tvAppVersion?.text = "App Version ${com.kevine.billzapplication.BuildConfig.VERSION_NAME}"
        binding?.tvLogOut?.setOnClickListener { logout() }

    }

    fun logout(){
        val sharedPrefs = requireContext().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
//        editor.remove(Constants.USER_ID)
        editor.putString(Constants.USER_ID, Constants.EMPTY_STRING)
        editor.putString(Constants.ACCESS_TOKEN,Constants.EMPTY_STRING)
        startActivity(Intent(requireContext(), Loginactivity::class.java))
        editor.apply()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}