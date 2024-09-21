package com.example.trackmate.Data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtils(){
    private val dateFormat = "dd"
    private val dayFormat = "EEE"
    private val calendar = Calendar.getInstance()

    private fun dateFormatter(pattern: String, dateInLong: Long): String{
        val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormatter.format(Date(dateInLong))
    }

    fun createDateList(): ArrayList<Long> {
        val returnList = ArrayList<Long>()

        for(i in 49 downTo 0){
            returnList.add(calendar.timeInMillis - 24*3600*1000*i)
        }
        for(i in 1..50){
            returnList.add(calendar.timeInMillis + 24*3600*1000*i)
        }

        return returnList
    }

    fun getDate(date: Long): String{
        return dateFormatter(pattern = dateFormat, dateInLong = date)
    }

    fun getDay(date: Long): String{
        return dateFormatter(pattern = dayFormat, dateInLong = date)
    }

    fun getCurrentDateInLong(): Long{
        return calendar.timeInMillis
    }

}
