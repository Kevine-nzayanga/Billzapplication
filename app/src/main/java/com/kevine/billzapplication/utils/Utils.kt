package com.kevine.billzapplication.utils

import java.text.DecimalFormat

class Utils {

    companion object{
        fun formatCurrency(amount: Double):String{
            val formatter = DecimalFormat("KES #,###.##")
            return  formatter.format(amount)
        }

    }
}