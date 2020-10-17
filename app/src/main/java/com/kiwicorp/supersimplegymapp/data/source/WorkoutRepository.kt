package com.kiwicorp.supersimplegymapp.data.source

import androidx.lifecycle.LiveData
import com.kiwicorp.supersimplegymapp.data.Workout
import com.kiwicorp.supersimplegymapp.data.WorkoutEntry
import com.kiwicorp.supersimplegymapp.data.WorkoutWithEntries
import com.kiwicorp.supersimplegymapp.data.source.local.WorkoutEntryDao
import com.kiwicorp.supersimplegymapp.data.source.local.WorkoutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val workoutEntryDao: WorkoutEntryDao
) {
    val workouts = workoutDao.observerWorkouts()

    suspend fun getWorkout(workoutId: String): WorkoutWithEntries {
        return withContext(Dispatchers.IO) {
            workoutDao.getWorkout(workoutId)
        }
    }

    suspend fun insertWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.insertWorkout(workout)
        }
    }

    suspend fun updateWorkout(workout: Workout): Int {
        return withContext(Dispatchers.IO) {
            workoutDao.updateWorkout(workout)
        }
    }

    suspend fun deleteWorkout(workout: Workout): Int {
        return withContext(Dispatchers.IO) {
            workoutDao.deleteWorkout(workout)
        }
    }

    suspend fun insertEntry(workoutEntry: WorkoutEntry) {
        withContext(Dispatchers.IO) {
            workoutEntryDao.insertEntry(workoutEntry)
        }
    }

    suspend fun updateEntry(workoutEntry: WorkoutEntry): Int {
        return withContext(Dispatchers.IO) {
            workoutEntryDao.updateEntry(workoutEntry)
        }
    }

    suspend fun deleteEntry(workoutEntry: WorkoutEntry): Int {
        return withContext(Dispatchers.IO) {
            workoutEntryDao.deleteEntry(workoutEntry)
        }
    }

    fun observeEntriesByActivityId(activityId: String): LiveData<List<WorkoutEntry>> {
        return workoutEntryDao.observeEntriesByActivityId(activityId)
    }

    suspend fun getEntriesByWorkoutId(workoutId: String): List<WorkoutEntry> {
        return withContext(Dispatchers.IO) {
            workoutEntryDao.getEntriesByWorkoutId(workoutId)
        }
    }
}