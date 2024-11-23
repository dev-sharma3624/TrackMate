package com.example.trackmate.Data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtils(){
    private val dateFormat = "dd"
    private val dayFormat = "EEE"
    private val dateMonthDayFormat = "dd MMM, EEE"
    private val calendar = Calendar.getInstance()
    private val currentTimeInMillis = calendar.timeInMillis

    private fun dateFormatter(pattern: String, dateInLong: Long): String{
        val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormatter.format(Date(dateInLong))
    }

    fun createDateList(): ArrayList<Long> {
        val returnList = ArrayList<Long>()

        for(i in 14 downTo 0){
            returnList.add(currentTimeInMillis - 24L*3600*1000*i)
            /*Log.d("NAMASTE", "Value of i: $i")
            Log.d("NAMASTE", "Last added element: ${getDateMonthAndDay(returnList.last())}")
            Log.d("NAMASTE", "Size of array: ${returnList.size}")*/
        }

        return returnList
    }

    fun getDate(date: Long): String{
        return dateFormatter(pattern = dateFormat, dateInLong = date)
    }

    fun getDay(date: Long): String{
        return dateFormatter(pattern = dayFormat, dateInLong = date)
    }

    fun getDateMonthAndDay(day: Long): String{
        return dateFormatter(pattern = dateMonthDayFormat, dateInLong = day)
    }

    fun getCurrentDateInLong(): Long{
        return currentTimeInMillis
    }

}
