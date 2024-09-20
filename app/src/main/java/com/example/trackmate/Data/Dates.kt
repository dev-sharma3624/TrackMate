package com.example.trackmate.Data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Dates(){

    val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")


    private fun createDateList(): List<Map<Int,String>> {
        val returnList = ArrayList<Map<Int, String>>()
        val calendar = Calendar.getInstance()
        val currentDate = calendar.get(Calendar.DAY_OF_MONTH) //day of the month starts from 1
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK) //day of the week starts from 1
        val currentMonth = calendar.get(Calendar.MONTH) //month starts from 0
        val currentYear = calendar.get(Calendar.YEAR)


        //condition to check for leap year
        if(currentYear % 4 == 0){

            if(currentMonth == Calendar.AUGUST){

                //adding the previous month dates
                for(i in 1..31){
                    val map = mutableMapOf<Int, String>()
                    map[i] = days[i-1]
                    returnList.add(map)
                }

                //adding the current month dates
                for(i in currentDate downTo  1){
                    val map = mutableMapOf<Int, String>()
                    map[i] = days[i-1]
                    returnList.add(map)
                }

                //adding the next month dates
                for(i in 1..31){
                    val map = mutableMapOf<Int, String>()
                    map[i] = days[i-1]
                    returnList.add(map)
                }


            }else if(currentMonth < Calendar.AUGUST){

            }else{

            }

        }else{

        }


        return returnList
    }
}
