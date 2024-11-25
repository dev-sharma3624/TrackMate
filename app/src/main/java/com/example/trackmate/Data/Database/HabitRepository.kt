package com.example.trackmate.Data.Database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    //This query will fetch details of habit along with true false value for checkbox
    fun getHabitInfoWithBooleanValue(targetDate: Long) : Flow<List<HabitInfoWithBooleanValue>>{
        return habitDao.getHabitInfoWithBooleanValue(targetDate)
    }

    //To fetch details of a specific habit along with its journal entries
    suspend fun getHabitWithJournalEntries(id: Long): HabitInfoWithJournal{
        return habitDao.getHabitWithJournalEntries(id)
    }









    //to add new habit to the habit list table
    suspend fun insertHabit(newHabit: Habit){
        habitDao.insertHabit(newHabit)
    }

    //to insert new journal entry in the habit journal : first, every night when the date
    //changes we need to make sure that new entries are added to the journal for the latest date
    suspend fun insertHabitJournal(newJournalEntry: HabitJournal){
        habitDao.insertHabitJournal(newJournalEntry)
    }










    //to update habit in habit list table, say when we need to set different time or name
    suspend fun updateHabit(updatedHabit: Habit){
        habitDao.updateHabit(updatedHabit)
    }


    //to update the journal entry, say if we want to change the amount of time the activity was performed
    suspend fun updateHabitJournal(updatedEntry: HabitJournal){
        habitDao.updateHabitJournal(updatedEntry)
    }







    //to delete a habit from the habitList table
    suspend fun deleteHabit(deletedHabit: Habit){
        habitDao.deleteHabit(deletedHabit)
    }

    //to delete a habit journal entry, when the checkbox is un-ticked
    //TODO: create an undo option for this as it is an expensive operation with more chances of mistake
    suspend fun deleteHabitJournal(deletedJournal: HabitJournal){
        habitDao.deleteHabitJournal(deletedJournal)
    }

}