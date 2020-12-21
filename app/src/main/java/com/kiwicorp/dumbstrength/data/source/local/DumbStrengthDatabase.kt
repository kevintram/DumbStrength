package com.kiwicorp.dumbstrength.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwicorp.dumbstrength.data.*

@Database(
    entities = [
        Activity::class,
        Workout::class,
        WorkoutEntry::class,
        Routine::class,
        RoutineEntry::class
    ], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DumbStrengthDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutEntryDao(): WorkoutEntryDao
    abstract fun routineDao(): RoutineDao
    abstract fun routineEntryDao(): RoutineEntryDao
}