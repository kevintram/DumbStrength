package com.kiwicorp.supersimplegymapp.ui.routines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository

class RoutinesViewModel @ViewModelInject constructor(
    private val routineRepository: RoutineRepository
): ViewModel() {
    val routines = routineRepository.routines

    private val _navigateToAddActivityFragment = MutableLiveData<Event<Unit>>()
    val navigateToAddActivityFragment: LiveData<Event<Unit>> = _navigateToAddActivityFragment

    fun navigateToAddActivity() {
        _navigateToAddActivityFragment.value = Event(Unit)
    }
}