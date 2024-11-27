package com.example.trackmate.Data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DateUtils @Inject constructor(){
    private val dateFormat = "dd"
    private val dayFormat = "EEE"
    private val dateMonthDayFormat = "dd MMM, EEE"
    private val currentTimeInMillis = Calendar.getInstance().timeInMillis

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

    //function to get millisecond time from midnight starting of day to midnight ending of day
    fun getStartAndEndInMillis(date: Long): Pair<Long, Long>{
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val startTime = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)

        val endTime = calendar.timeInMillis

        return Pair(startTime, endTime)
    }

}
