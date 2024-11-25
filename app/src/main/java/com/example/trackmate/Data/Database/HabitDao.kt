package com.example.trackmate.Data.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HabitDao {

    //This query will fetch details of habit along with true false value for checkbox
    @Query("Select HabitsList.*, CASE WHEN HabitJournal.doneOn = :targetDate THEN 1 ELSE 0 END as isDoneToday from HabitsList inner join HabitJournal on HabitsList.id = HabitJournal.habitId where HabitsList.createdOn <= :targetDate")
    abstract fun getHabitInfoWithBooleanValue(targetDate: Long) : Flow<List<HabitInfoWithBooleanValue>>

    //To fetch details of a specific habit along with its journal entries
    @Transaction
    @Query("Select * from HabitsList where id = :id")
    abstract suspend fun getHabitWithJournalEntries(id: Long): HabitInfoWithJournal






    //to add new habit to the habit list table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabit(newHabit: Habit)

    //to insert new journal entry in the habit journal : first, every night when the date
    //changes we need to make sure that new entries are added to the journal for the latest date
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHabitJournal(newJournalEntry: HabitJournal)





    //to update habit in habit list table, say when we need to set different time or name
    @Update
    abstract suspend fun updateHabit(updatedHabit: Habit)

    //to update the journal entry, say if we want to change the amount of time the activity was performed
    @Update
    abstract suspend fun updateHabitJournal(updatedEntry: HabitJournal)





    //to delete a habit from the habitList table
    @Delete
    abstract suspend fun deleteHabit(deletedHabit: Habit)


    //to delete a habit journal entry, when the checkbox is un-ticked
    //TODO: create an undo option for this as it is an expensive operation with more chances of mistake
    @Delete
    abstract suspend fun deleteHabitJournal(deletedJournal: HabitJournal)
}