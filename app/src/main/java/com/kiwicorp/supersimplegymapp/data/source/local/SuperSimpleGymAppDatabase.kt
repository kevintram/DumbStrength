package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.Workout
import com.kiwicorp.supersimplegymapp.data.Entry

@Database(
    entities = [
        Activity::class,
        Workout::class,
        Entry::class
    ], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SuperSimpleGymAppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutComponentDao(): EntryDao
}