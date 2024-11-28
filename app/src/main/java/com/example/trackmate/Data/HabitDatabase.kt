package com.example.trackmate.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Habit::class, HabitJournal::class],
    version = 1,
    exportSchema = true
)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
}