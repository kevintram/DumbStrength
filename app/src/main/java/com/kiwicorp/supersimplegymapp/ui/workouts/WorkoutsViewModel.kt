package com.kiwicorp.supersimplegymapp.ui.workouts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.data.*
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class WorkoutsViewModel @ViewModelInject constructor(
    private val workoutRepository: WorkoutRepository,
    private val activityRepository: ActivityRepository
): ViewModel() {
    val workouts = workoutRepository.workouts

    fun insert() {
        viewModelScope.launch {
            val activity = Activity("1ct Bench Press")
            activityRepository.insertActivity(activity)

            val activityTwo = Activity("Iron Wolf Monday","10 squats, 10 Push Ups, 10 Sit Ups")
            activityRepository.insertActivity(activityTwo)

            val workout = Workout(ZonedDateTime.now())
            workoutRepository.insertWorkout(workout)

            val entry = Entry(activity.id, workout.id,"I AM a description")
            workoutRepository.insertEntry(entry)

            val entryTwo = Entry(activityTwo.id, workout.id, "DESC")
            workoutRepository.insertEntry(entryTwo)
        }
    }
}