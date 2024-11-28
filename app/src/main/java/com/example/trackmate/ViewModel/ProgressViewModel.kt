package com.example.trackmate.ViewModel

import androidx.lifecycle.ViewModel
import com.example.trackmate.Data.HabitInfoWithJournal
import com.example.trackmate.Data.HabitRepository
import com.example.trackmate.ProgressScreenHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    private val habitId = ProgressScreenHabit.id

    var habitInfoWithJournalEntries: Flow<HabitInfoWithJournal> = emptyFlow()

    init {
        /*viewModelScope.launch {
            habitInfoWithJournalEntries = habitRepository.getHabitWithJournalEntries(habitId)
        }*/
    }

}