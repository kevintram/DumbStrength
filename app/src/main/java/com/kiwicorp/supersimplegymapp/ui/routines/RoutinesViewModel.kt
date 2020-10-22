package com.kiwicorp.supersimplegymapp.ui.routines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Routine
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository
import com.kiwicorp.supersimplegymapp.ui.routinecommon.RoutinesListAdapter

class RoutinesViewModel @ViewModelInject constructor(
    private val routineRepository: RoutineRepository
): ViewModel(), RoutinesListAdapter.OnRoutineClickListener {
    val routines = routineRepository.routines

    private val _navigateToAddRoutineFragment = MutableLiveData<Event<Unit>>()
    val navigateToAddRoutineFragment: LiveData<Event<Unit>> = _navigateToAddRoutineFragment

    private val _navigateToEditRoutineFragment = MutableLiveData<Event<String>>()
    val navigateToEditRoutineFragment: LiveData<Event<String>> = _navigateToEditRoutineFragment

    fun navigateToAddRoutineFragment() {
        _navigateToAddRoutineFragment.value = Event(Unit)
    }

    fun navigateToEditRoutineFragment(routineId: String) {
        _navigateToEditRoutineFragment.value = Event(routineId)
    }

    override fun onRoutineClick(routine: Routine) {
        navigateToEditRoutineFragment(routine.id)
    }
}