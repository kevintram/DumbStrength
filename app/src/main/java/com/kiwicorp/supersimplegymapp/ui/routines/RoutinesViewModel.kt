package com.kiwicorp.supersimplegymapp.ui.routines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Routine
import com.kiwicorp.supersimplegymapp.data.RoutineWithEntries
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository
import com.kiwicorp.supersimplegymapp.ui.routinecommon.RoutinesListAdapter
import kotlinx.coroutines.launch

class RoutinesViewModel @ViewModelInject constructor(
    private val routineRepository: RoutineRepository
): ViewModel(), RoutinesListAdapter.OnRoutineClickListener {
    // use MediatorLiveData that subscribes to routineRepository.routines so swapping routines
    // doesn't cause flickering
    val routines = MediatorLiveData<List<RoutineWithEntries>>().apply {
        addSource(routineRepository.routines) {
            value = it
        }
    }

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

    fun swapRoutines(index: Int, targetIndex: Int) {
        val newRoutines = routines.value!!.toMutableList()

        val routineWithEntries = newRoutines[index]
        val target = newRoutines[targetIndex]
        // swap
        newRoutines[index] = target
        newRoutines[targetIndex] = routineWithEntries
        // update index
        routineWithEntries.routine.index = targetIndex
        target.routine.index = index
        // update list
        routines.value = newRoutines
        // update in database
        viewModelScope.launch {
            routineRepository.updateRoutine(routineWithEntries.routine)
        }
        viewModelScope.launch {
            routineRepository.updateRoutine(target.routine)
        }
    }

    override fun onRoutineClick(routine: Routine) {
        navigateToEditRoutineFragment(routine.id)
    }
}