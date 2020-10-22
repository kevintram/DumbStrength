package com.kiwicorp.supersimplegymapp.ui.chooseroutine

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Routine
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository
import com.kiwicorp.supersimplegymapp.ui.routinecommon.RoutinesListAdapter

class ChooseRoutineViewModel @ViewModelInject constructor(
    routineRepository: RoutineRepository
): ViewModel(), RoutinesListAdapter.OnRoutineClickListener {
    val routines = routineRepository.routines

    private val _navigateToAddWorkoutFragment = MutableLiveData<Event<String?>>()
    val navigateToAddWorkoutFragment: LiveData<Event<String?>> = _navigateToAddWorkoutFragment

    fun navigateToAddWorkoutFragment(routineId: String?) {
        _navigateToAddWorkoutFragment.value = Event(routineId)
    }

    override fun onRoutineClick(routine: Routine) {
        navigateToAddWorkoutFragment(routine.id)
    }
}