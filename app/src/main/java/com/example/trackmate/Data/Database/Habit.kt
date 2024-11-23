package com.example.trackmate.Data.Database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(tableName = "HabitsList")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "habitName")
    val habitName: String,

    @ColumnInfo(name = "createdOn")
    val createdOn: Long,

    @ColumnInfo(name = "timeSet")
    val timeSet: Long
)








@Entity(
    tableName = "HabitJournal",
    foreignKeys = [ForeignKey(
        entity = Habit::class,
        parentColumns = ["id"],
        childColumns = ["habitId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HabitJournal(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "habitId")
    val habitId: Long,

    @ColumnInfo(name = "doneOn")
    val doneOn: Long,

    @ColumnInfo(name = "timePeriod")
    val timePeriod: Float
)









data class HabitInfoWithJournal(
    @Embedded
    val habit: Habit,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val journal: List<HabitJournal>
)
