package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kiwicorp.supersimplegymapp.data.Workout
import com.kiwicorp.supersimplegymapp.data.WorkoutWithEntries

@Dao
interface WorkoutDao {
    @Transaction
    @Query("SELECT * FROM workouts")
    fun observerWorkouts(): LiveData<List<WorkoutWithEntries>>

    @Transaction
    @Query("SELECT * FROM workouts")
    suspend fun getWorkouts(): List<WorkoutWithEntries>

    @Insert
    suspend fun insertWorkout(workout: Workout)
}