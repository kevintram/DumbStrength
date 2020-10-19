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

    private val _navigateToEditWorkoutFragment = MutableLiveData<Event<String>>()
    val navigateToEditWorkoutFragment: LiveData<Event<String>> = _navigateToEditWorkoutFragment

    private val _navigateToChooseRoutineFragment = MutableLiveData<Event<Unit>>()
    val navigateToChooseRoutineFragment: LiveData<Event<Unit>> = _navigateToChooseRoutineFragment

    fun navigateToEditWorkoutFragment(workoutId: String) {
        _navigateToEditWorkoutFragment.value = Event(workoutId)
    }

    fun navigateToChooseRoutineFragment() {
        _navigateToChooseRoutineFragment.value = Event(Unit)
    }
}