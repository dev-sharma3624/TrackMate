package com.example.trackmate

import android.app.Application
import androidx.room.Room
import com.example.trackmate.Data.Database.HabitDao
import com.example.trackmate.Data.Database.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Graph {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): HabitDatabase{
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            "habit.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: HabitDatabase): HabitDao{
        return database.habitDao()
    }

}