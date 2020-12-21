package com.kiwicorp.dumbstrength.ui.routines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.dumbstrength.Event
import com.kiwicorp.dumbstrength.data.Routine
import com.kiwicorp.dumbstrength.data.RoutineWithEntries
import com.kiwicorp.dumbstrength.data.source.RoutineRepository
import com.kiwicorp.dumbstrength.ui.routinecommon.RoutinesListAdapter
import kotlinx.coroutines.launch

class RoutinesViewModel @ViewModelInject constructor(
    private val routineRepository: RoutineRepository
): ViewModel(), RoutinesListAdapter.OnRoutineClickListener {
    // use MediatorLiveData that subscribes to routineRepository.routines so swapping routines
    // doesn't cause flickering
    val routines = MediatorLiveData<List<RoutineWithEntries>>().apply {
        addSource(routineRepository.routines) { list ->
            // sort here because if routines are reordered and the user navigates away and back,
            // dao will initially return a list with the old ordering and then a list with the
            // correct ordering causing the recycler view to show a reordering animation
            value = list.sortedBy { it.routine.index }
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

        newRoutines[index] = target
        newRoutines[targetIndex] = routineWithEntries

        routineWithEntries.routine.index = targetIndex
        target.routine.index = index

        routines.value = newRoutines
    }

    /**
     * Updates the order of the routines.
     */
    fun updateRoutineOrder() {
        routines.value?.let{
            for (routineWithEntries in it) {
                viewModelScope.launch {
                    routineRepository.updateRoutine(routineWithEntries.routine)
                }
            }
        }
    }

    override fun onRoutineClick(routine: Routine) {
        navigateToEditRoutineFragment(routine.id)
    }
}