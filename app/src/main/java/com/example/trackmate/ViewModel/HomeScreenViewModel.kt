package com.example.trackmate.ViewModel


import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.trackmate.Data.DateUtils
import com.example.trackmate.Data.Habits
import kotlinx.coroutines.flow.Flow

class HomeScreenViewModel: ViewModel() {

    private val dateUtils = DateUtils()
    val dateList = dateUtils.createDateList()

//    val habitList: Flow<List<Habits>> = getHabitList()
    val habitList = emptyList<Habits>()
    val currentDate = dateUtils.getCurrentDateInLong()


    private var _topBarHeading = "Today"
    var topBarHeading = _topBarHeading

    fun topBarHeadingDecider(date: Long){
        if(currentDate == date)  _topBarHeading = "Today"
        else if(date == currentDate + 24*3600*1000) _topBarHeading = "Tomorrow"
        else if(date == currentDate - 24*3600*1000) _topBarHeading = "Yesterday"
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