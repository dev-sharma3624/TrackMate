package com.example.trackmate.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmate.Data.Habit
import com.example.trackmate.Data.HabitInfoWithJournal
import com.example.trackmate.Data.HabitRepository
import com.example.trackmate.ProgressScreenHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

val tagPvm = "NAMASTE"

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    private val habitId = ProgressScreenHabit.id

    lateinit var habitInfoWithJournalEntries: StateFlow<HabitInfoWithJournal>

    private var _latestActivityTime = MutableStateFlow("00:00")
    val latestActivityTime: StateFlow<String> = _latestActivityTime

    private var _deviationFromLastWeek = MutableStateFlow(0f)
    val deviationFromLastWeek : StateFlow<Float> = _deviationFromLastWeek

    private val _graphPlottingValues : MutableStateFlow<List<Pair<Long, Float>>?> =
        MutableStateFlow(null)

    val graphPlottingValues: StateFlow<List<Pair<Long, Float>>?> =
        _graphPlottingValues


    init {

        viewModelScope.launch {
            habitInfoWithJournalEntries = habitRepository
                .getHabitWithJournalEntries(habitId!!)
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    HabitInfoWithJournal(
                        Habit(habitName = "", createdOn = 0L, timeSet = ""),
                        emptyList()
                    )
                )

            /*try {
                habitRepository.getHabitWithJournalEntries(habitId!!).collect{
                    _habitInfoWithJournalEntries.value = it
                }
            }catch (e: NoSuchElementException){
                _habitInfoWithJournalEntries.value = HabitInfoWithJournal(
                    Habit(habitName = "", createdOn = 0L, timeSet = ""),
                    emptyList()
                )
            }*/
//            _habitInfoWithJournalEntries = habitRepository.getHabitWithJournalEntries(habitId)

            /*if (habitInfoWithJournalEntries.value.journal.isNotEmpty()){
                withContext(Dispatchers.Default){

                    Log.d(tagPvm, "inside with context")

                    setLatestActivityTime()

                    setDeviationFromLastWeek()

                    setGraphPlottingValues()

                }
            }*/
        }
    }

    private fun isActivityDoneToday(): Boolean{

        val currentTime = dateUtils.getCurrentDateInLong()

        val (startTime, endTime) = dateUtils.getStartAndEndInMillis(currentTime)

        return currentTime in startTime..endTime
    }

    fun setLatestActivityTime(){

        val time = (if(isActivityDoneToday()){
            habitInfoWithJournalEntries.value.journal.maxBy {
                it.doneOn
            }.timePeriod
        }else{
            0.0f
        })

        Log.d(tagPvm, "value of time: $time")

        _latestActivityTime.value = convertFloatToTimeString(time)

        Log.d(tagPvm, "value of formatted time: ${_latestActivityTime.value}")

    }

    fun setDeviationFromLastWeek(){
        Log.d(tagPvm, "inside deviation")
        val (startDate, endDate) = dateUtils.getWeekRange(7)
        var startTime = dateUtils.getStartAndEndInMillis(startDate).first
        var endTime = dateUtils.getStartAndEndInMillis(endDate).second

        var lastWeekRecord = 0f
        var currentWeekRecord = 0f

        habitInfoWithJournalEntries.value.journal
            .filter {
                it.doneOn in startTime..endTime
            }.forEach {
                lastWeekRecord += it.timePeriod
                Log.d(tagPvm, "last week record: $lastWeekRecord")
            }


        val currentWeekRange = dateUtils.getWeekRange(0)

        startTime = dateUtils.getStartAndEndInMillis(currentWeekRange.first).first
        endTime = dateUtils.getStartAndEndInMillis(currentWeekRange.second).second

        habitInfoWithJournalEntries.value.journal
            .filter {
                it.doneOn in startTime..endTime
            }.forEach {
                currentWeekRecord += it.timePeriod
                Log.d(tagPvm, "current week record: $currentWeekRecord")
            }

        _deviationFromLastWeek.value = currentWeekRecord - lastWeekRecord

    }

    fun setGraphPlottingValues(){
        Log.d(tagPvm, "inside setGraph")

        val graphValues = mutableListOf<Pair<Long, Float>>()

        val startTime = dateUtils.getStartAndEndInMillis(dateUtils.getCurrentDateInLong() - 6*24*3600*1000L).first
        val endTime = dateUtils.getStartAndEndInMillis(dateUtils.getCurrentDateInLong()).second

        habitInfoWithJournalEntries.value.journal.filter {
            it.doneOn in startTime..endTime
        }.forEach {
            graphValues.add(Pair(it.doneOn, it.timePeriod))
        }

        graphValues.sortBy {
            it.first
        }

        _graphPlottingValues.value = graphValues
    }

    fun getDayTextForGraph(date: Long): String{
        return dateUtils.getDay(date)
    }

    fun getDateTextForGraph(date: Long): String{
        return  dateUtils.getDateAndMonth(date)
    }


    @SuppressLint("DefaultLocale")
    fun convertFloatToTimeString(num: Float): String{
        val int = num.toInt()
        Log.d(tagPvm, "int value: $int")
        val fraction = ((num % 1) * 100).toInt()
        Log.d(tagPvm, "fraction value: $fraction")
        Log.d(tagPvm, "string: ${String.format("%02d:%02d", int, fraction)}")
        return String.format("%02d:%02d", int, fraction)
    }

    fun createTimeStamp(doneOn: Long, timePeriod: Float): String{
        val timeStamp = dateUtils.getTimeStamp(doneOn, 0f)

        timeStamp.plus(" - ")

        timeStamp.plus(dateUtils.getTimeStamp(doneOn, timePeriod))

        return timeStamp
    }

    fun createDateStamp(doneOn: Long): String{
        return dateUtils.getDateMonthAndDay(doneOn)
    }

}
