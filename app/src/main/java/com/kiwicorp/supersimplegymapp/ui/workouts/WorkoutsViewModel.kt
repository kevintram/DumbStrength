package com.kiwicorp.supersimplegymapp.ui.workouts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import java.time.LocalDate

class WorkoutsViewModel @ViewModelInject constructor(
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    val workouts = workoutRepository.workouts

    val workoutsGroupedByDate = Transformations.map(workoutRepository.workouts) { workouts ->
        workouts.groupBy { it.workout.date }
    }

    private val _navigateToEditWorkoutFragment = MutableLiveData<Event<String>>()
    val navigateToEditWorkoutFragment: LiveData<Event<String>> = _navigateToEditWorkoutFragment

    private val _navigateToChooseRoutineFragment = MutableLiveData<Event<Unit>>()
    val navigateToChooseRoutineFragment: LiveData<Event<Unit>> = _navigateToChooseRoutineFragment

    private val _navigateToWorkoutsFragment = MutableLiveData<Event<Unit>>()
    val navigateToWorkoutsFragment: LiveData<Event<Unit>> = _navigateToWorkoutsFragment

    private val _navigateToWorkoutCalendarFragment = MutableLiveData<Event<Unit>>()
    val navigateToWorkoutCalendarFragment: LiveData<Event<Unit>> = _navigateToWorkoutCalendarFragment

    private val _scrollToDate = MutableLiveData<Event<LocalDate>>()
    val scrollToDate: LiveData<Event<LocalDate>> = _scrollToDate

    fun navigateToEditWorkoutFragment(workoutId: String) {
        _navigateToEditWorkoutFragment.value = Event(workoutId)
    }

    fun navigateToChooseRoutineFragment() {
        _navigateToChooseRoutineFragment.value = Event(Unit)
    }

    fun navigateToWorkoutsFragment() {
        _navigateToWorkoutsFragment.value = Event(Unit)
    }

    fun navigateToWorkoutCalendarFragment() {
        _navigateToWorkoutCalendarFragment.value = Event(Unit)
    }

    fun scrollToDate(date: LocalDate) {
        _scrollToDate.value = Event(date)
    }

    fun navigateToWorkoutsFragmentAndScrollToDate(date: LocalDate) {
        navigateToWorkoutsFragment()
        scrollToDate(date)
    }
}