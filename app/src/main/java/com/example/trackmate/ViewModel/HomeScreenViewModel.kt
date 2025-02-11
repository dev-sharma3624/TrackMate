package com.example.trackmate.ViewModel


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmate.Data.Habit
import com.example.trackmate.Data.HabitInfoWithBooleanValue
import com.example.trackmate.Data.HabitInfoWithJournal
import com.example.trackmate.Data.HabitJournal
import com.example.trackmate.Data.HabitRepository
import com.example.trackmate.ProgressScreenHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


val taghvm = "NAMASTE"

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateUtils: DateUtils
) : ViewModel() {

    val dateList: ArrayList<Long>
    val currentDate: Long

    //todo we just need to change dummyHabitList to habitList from source of data
    var habitList: Flow<List<HabitInfoWithBooleanValue>> = emptyFlow()


    private var _topBarHeading: MutableState<String> = mutableStateOf("Today")
    var topBarHeading: State<String> = _topBarHeading





    //todo we just need to change dummyHabitList to habitList from source of data
    val dummyHabitList = flowOf(listOf(
        HabitInfoWithBooleanValue(
            Habit(0L, "Meditation", 1732615759330L, "10:00:AM"),
            true
        ),
        HabitInfoWithBooleanValue(
            Habit(0L, "Meditation", 1732615759330L, "10:00:AM"),
            true)
    ))




    init {
        currentDate = dateUtils.getCurrentDateInLong()
        dateList = dateUtils.createDateList()
        viewModelScope.launch {
            val (startTime, endTime) = dateUtils.getStartAndEndInMillis(currentDate)
            habitList = habitRepository.getHabitInfoWithBooleanValue(
                startTime = startTime,
                endTime = endTime
            )
            if(ProgressScreenHabit.id == null){
                ProgressScreenHabit.id = habitRepository.setHabitForProgressScreen()
            }
        }
    }






    fun topBarHeadingDecider(date: Long){
        if(currentDate == date)  _topBarHeading.value = "Today"
        else if(date == currentDate + 24*3600*1000L) _topBarHeading.value = "Tomorrow"
        else if(date == currentDate - 24*3600*1000L) _topBarHeading.value = "Yesterday"
        else _topBarHeading.value = dateUtils.getDateMonthAndDay(date)
    }

    fun getDateInString(date: Long): String{
        return dateUtils.getDate(date)
    }

    fun getDayInString(date: Long): String{
        return dateUtils.getDay(date)
    }


    fun getHabitList(date: Long) {
        val (startTime, endTime) = dateUtils.getStartAndEndInMillis(date)
        viewModelScope.launch {
            habitList = habitRepository.getHabitInfoWithBooleanValue(startTime = startTime, endTime = endTime)
        }
    }

    @SuppressLint("DefaultLocale")
    fun createHabitForInsertion(habitName: String, hour: Int, min: Int){
        val str = "%02d:%02d %s"
        val isAmPm = if(hour <= 12) "AM" else "PM"
        val hourTimeIn12hrFormat = if(isAmPm == "PM") hour%12 else hour

        val time = String.format(str, hourTimeIn12hrFormat, min, isAmPm)

        val newHabit = Habit(
            habitName = habitName,
            timeSet = time,
            createdOn = dateUtils.getCurrentDateInLong()
        )

        insertHabit(newHabit)
    }

    fun insertHabit(newHabit: Habit): Boolean{
        var isSuccess = false
        viewModelScope.launch {
            try {
                habitRepository.insertHabit(newHabit)
                isSuccess = true
            }catch (_: Exception){
                isSuccess = false
            }
        }
        return isSuccess
    }

    fun insertHabitJournal(newJournalEntry: HabitJournal){
        viewModelScope.launch {
            habitRepository.insertHabitJournal(newJournalEntry)
        }
    }

    fun updateHabit(updatedHabit: Habit): Boolean{
        var isSuccess = false
        viewModelScope.launch {
            try {
                habitRepository.updateHabit(updatedHabit)
                isSuccess = true
            }catch (_: Exception){
                isSuccess = false
            }
        }
        return isSuccess
    }

    fun updateHabitJournal(updatedEntry: HabitJournal): Boolean{
        var isSuccess = false
        viewModelScope.launch {
            try {
                habitRepository.updateHabitJournal(updatedEntry)
                isSuccess = true
            }catch (_: Exception){
                isSuccess = false
            }
        }
        return isSuccess
    }

    fun deleteHabit(deletedHabit: Long): Boolean{
        var isSuccess = false
        viewModelScope.launch {
            try {
                habitRepository.deleteHabit(deletedHabit)
                isSuccess = true
            }catch (_: Exception){
                isSuccess = false
            }
        }
        return isSuccess
    }

    fun deleteHabitJournal(deletedHabitId: Long, date: Long): Boolean{
        var isSuccess = false
        val (startTime, endTime) = dateUtils.getStartAndEndInMillis(date)
        viewModelScope.launch {
            try {
                habitRepository.deleteHabitJournal(
                    deletedHabitId = deletedHabitId,
                    startTime = startTime,
                    endTime = endTime
                )
                isSuccess = true
            }catch (_: Exception){
                isSuccess = false
            }
        }
        return isSuccess
    }

    fun setHabitIdForProgressScreen(){
        viewModelScope.launch {
            ProgressScreenHabit.id = habitRepository.setHabitForProgressScreen()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(context: Context, habit: HabitInfoWithBooleanValue){
        val alarmIntent = Intent(
            context,
            AlarmReceiver::class.java)
            .putExtra("TITLE", habit.habit.habitName)
            .putExtra("ID", habit.habit.id)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habit.habit.id.toInt(),
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, getTimeForAlarm(habit.habit.timeSet), pendingIntent)
    }

    //02:50 PM
    fun getTimeForAlarm(str: String): Long{
        val isAmPm = str.substring(6)

        val hour = if(isAmPm == "AM") str.substring(0, 2).toInt() else str.substring(0, 2).toInt() + 12
        val min = str.substring(3,5).toInt()

        val time = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
        }

        return  time.timeInMillis
    }

}