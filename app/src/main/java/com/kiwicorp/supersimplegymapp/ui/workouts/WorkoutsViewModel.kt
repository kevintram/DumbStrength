package com.kiwicorp.supersimplegymapp.ui.workouts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.*
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class WorkoutsViewModel @ViewModelInject constructor(
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    val workouts = workoutRepository.workouts

    private val _navigateToAddWorkoutFragment = MutableLiveData<Event<Unit>>()
    val navigateToAddEditWorkoutFragment: LiveData<Event<Unit>> = _navigateToAddWorkoutFragment

    fun navigateToAddWorkoutFragment() {
        _navigateToAddWorkoutFragment.value = Event(Unit)
    }
}