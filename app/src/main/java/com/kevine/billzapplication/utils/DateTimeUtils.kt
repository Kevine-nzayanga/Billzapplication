package com.kevine.billzapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import  java.time.temporal.TemporalAdjuster.*
import java.time.temporal.TemporalAdjusters.nextOrSame
import java.time.temporal.TemporalAdjusters.previousOrSame
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.of
import java.time.format.DateTimeFormatter

//import java.util.Calendar.MONDAY
//import java.util.Calendar.SUNDAY

class DateTimeUtils {
    companion object{
        fun getFirstDayOfWeek():String{
            val now = LocalDateTime.now()
          val first=  now.with(previousOrSame(MONDAY))
            return  formatDateDDMMyyyy(first)


        }

        fun getLastDayOfWeek(): String {
            val now = LocalDateTime.now()
            val first=  now.with(nextOrSame(SUNDAY))
            return  formatDateDDMMyyyy(first)

        }
        fun formatDateDDMMyyyy(LocalDate:LocalDateTime):String{
            val sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return sdf.format(LocalDate)
        }

        fun getDateOfWeekDay(day:String): String {
            val now = LocalDateTime.now()
            val date= now.with(nextOrSame(of(day.toInt())))
            return formatDateDDMMyyyy(date)
        }

        fun getCurrentYear():String{
            val now= LocalDateTime.now()
            return now.year.toString()
        }
    }
}