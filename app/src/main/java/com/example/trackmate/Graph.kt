package com.example.trackmate

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.trackmate.Data.HabitDao
import com.example.trackmate.Data.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Graph {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            "habit.db"
        ).setQueryCallback({ sqlQuery, bindArgs ->
            Log.d("RoomSQL", "Query: $sqlQuery, Args: $bindArgs")
        }, Executors.newSingleThreadExecutor()).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: HabitDatabase): HabitDao {
        return database.habitDao()
    }

}