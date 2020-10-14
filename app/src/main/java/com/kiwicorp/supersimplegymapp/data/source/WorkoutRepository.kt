package com.kiwicorp.supersimplegymapp.data.source

import androidx.lifecycle.LiveData
import com.kiwicorp.supersimplegymapp.data.Workout
import com.kiwicorp.supersimplegymapp.data.Entry
import com.kiwicorp.supersimplegymapp.data.EntryWithActivity
import com.kiwicorp.supersimplegymapp.data.source.local.EntryDao
import com.kiwicorp.supersimplegymapp.data.source.local.WorkoutDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val entryDao: EntryDao
) {
    val workouts = workoutDao.observerWorkouts()

    suspend fun insertWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.insertWorkout(workout)
        }
    }

    suspend fun insertEntry(entry: Entry) {
        withContext(Dispatchers.IO) {
            entryDao.insertEntry(entry)
        }
    }

    fun observeEntriesByActivityId(activityId: String): LiveData<List<Entry>> {
        return entryDao.observeEntriesByActivityId(activityId)
    }
}