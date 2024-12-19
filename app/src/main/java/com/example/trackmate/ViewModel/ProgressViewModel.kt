package com.example.trackmate.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackmate.Data.Habit
import com.example.trackmate.Data.HabitInfoWithJournal
import com.example.trackmate.Data.HabitJournal
import com.example.trackmate.Data.HabitRepository
import com.example.trackmate.ProgressScreenHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    private val habitId = ProgressScreenHabit.id

    private var habitInfoWithJournalEntries = MutableStateFlow(
        HabitInfoWithJournal(
            Habit(habitName = "", createdOn = 0L, timeSet = ""),
            emptyList()
        )
    )

    lateinit var topBarHeading: String

    private var _latestActivityTime = MutableStateFlow("")
    val latestActivityTime: StateFlow<String> = _latestActivityTime


    init {
        viewModelScope.launch {
            habitRepository.getHabitWithJournalEntries(habitId).collect{
                habitInfoWithJournalEntries.value = it
            }

            withContext(Dispatchers.Default){
                if(!::topBarHeading.isInitialized){
                    topBarHeading = habitInfoWithJournalEntries.value.habit.habitName
                }





            }
        }
    }

    private fun isActivityDoneToday(): Boolean{
        val currentTime = dateUtils.getCurrentDateInLong()
        val (startTime, endTime) = dateUtils.getStartAndEndInMillis(currentTime)
        return currentTime in startTime..endTime
    }

    fun setLatestActivityTime(){
        val time = {if(isActivityDoneToday()){
            habitInfoWithJournalEntries.value.journal.maxBy {
                it.doneOn
            }.timePeriod
        }else{
            0f
        }}.toString().replace(".", ":").substring(0, 4)

        _latestActivityTime.value = if(time.length == 4) "0".plus(time) else time

    }

}