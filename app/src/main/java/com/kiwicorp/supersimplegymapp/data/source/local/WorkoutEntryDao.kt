package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiwicorp.supersimplegymapp.data.WorkoutEntry

@Dao
interface WorkoutEntryDao {
    @Query("SELECT * FROM workout_entries WHERE workout_entry_activity_id = :activityId")
    fun observeEntriesByActivityId(activityId: String): LiveData<List<WorkoutEntry>>

    @Query("SELECT * FROM workout_entries WHERE workout_entry_workout_creator_id = :workoutId")
    suspend fun getEntriesByWorkoutId(workoutId: String): List<WorkoutEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(workoutEntry: WorkoutEntry)

    @Update
    suspend fun updateEntry(workoutEntry: WorkoutEntry): Int

    @Delete
    suspend fun deleteEntry(workoutEntry: WorkoutEntry): Int
}