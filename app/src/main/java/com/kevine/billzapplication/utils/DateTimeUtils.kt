package com.kevine.billzapplication.utils

import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters.nextOrSame
import java.time.temporal.TemporalAdjusters.previousOrSame
import java.time.DayOfWeek.MONDAY
import java.time.DayOfWeek.SUNDAY
import java.time.DayOfWeek.of
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

//import java.util.Calendar.MONDAY
//import java.util.Calendar.SUNDAY

class DateTimeUtils {
    companion object{
        fun formatDate(LocalDate:LocalDateTime):String {
            val sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return sdf.format(LocalDate)
        }

        fun formatDate2():String{
            val now  = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:SS.zzzzzz")
            return formatter.format(now)
        }


        fun getFirstDayOfWeek():String{
            val now = LocalDateTime.now()
          val first=  now.with(previousOrSame(MONDAY))
            return  formatDate(first)

        }

        fun getFirstDayOfMonth(): String {
            val now = LocalDateTime.now()
            val firstDay = now.with(TemporalAdjusters.firstDayOfMonth())
            return formatDate(firstDay)
        }

        fun getLastDayOfMonth(): String {
            val now = LocalDateTime.now()
            val lastDay = now.with(TemporalAdjusters.lastDayOfMonth())
            return formatDate(lastDay)
        }

        fun getCurrentMonth(): String {
            val now = LocalDateTime.now()
            return now.month.value.toString()
        }


        fun getLastDayOfWeek(): String {
            val now = LocalDateTime.now()
            val first=  now.with(nextOrSame(SUNDAY))
            return  formatDate(first)

        }

        fun createDateFromDayAndMonth(day: Int, month: Int): String {
            val now = LocalDateTime.now()
            val date = now.withMonth(month).withDayOfMonth(day)
            return formatDate(date)
        }




        fun getDateOfWeekDay(day:String): String {
            val now = LocalDateTime.now()
            val date= now.with(nextOrSame(of(day.toInt())))
            return formatDate(date)
        }
        fun createDateFromDay(dayOfMonth: String): String {
            val now = LocalDateTime.now()
            val date = now.withDayOfMonth(dayOfMonth.toInt())
            return formatDate(date)
        }

        fun formatDateReadable(date: String): String {
            val originalFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateToFormat = LocalDate.parse(date, originalFormat)
            val readableFormat = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
            return readableFormat.format(dateToFormat)
        }

        fun getCurrentYear():String{
            val now= LocalDateTime.now()
            return now.year.toString()
        }
        fun formatCurrency(amount: Double):String{
            val formatter = DecimalFormat("KES #,###.##")
            return  formatter.format(amount)
        }

    }
}

//zero means false one means true