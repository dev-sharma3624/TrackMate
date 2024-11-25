package com.example.trackmate.ViewModel


import androidx.lifecycle.ViewModel
import com.example.trackmate.Data.DateUtils
import com.example.trackmate.Data.Database.Habit

class HomeScreenViewModel: ViewModel() {

    private val dateUtils = DateUtils()
    val dateList = dateUtils.createDateList()

//    val habitList: Flow<List<Habits>> = getHabitList()
    val habitList = emptyList<Habit>()
    val currentDate = dateUtils.getCurrentDateInLong()


    private var _topBarHeading = "Today"
    var topBarHeading = _topBarHeading

    fun topBarHeadingDecider(date: Long){
        if(currentDate == date)  _topBarHeading = "Today"
        else if(date == currentDate + 24*3600*1000L) _topBarHeading = "Tomorrow"
        else if(date == currentDate - 24*3600*1000L) _topBarHeading = "Yesterday"
        else _topBarHeading = dateUtils.getDateMonthAndDay(date)
    }

    fun getDateInString(date: Long): String{
        return dateUtils.getDate(date)
    }

    fun getDayInString(date: Long): String{
        return dateUtils.getDay(date)
    }


    /*private fun getHabitList(): Flow<List<Habits>> {

    }*/

    fun updateHabitList(habitId: Int) {

    }


}