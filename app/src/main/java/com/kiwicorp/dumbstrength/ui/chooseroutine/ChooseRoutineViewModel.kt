package com.kiwicorp.dumbstrength.ui.chooseroutine

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiwicorp.dumbstrength.Event
import com.kiwicorp.dumbstrength.data.Routine
import com.kiwicorp.dumbstrength.data.source.RoutineRepository
import com.kiwicorp.dumbstrength.ui.routinecommon.RoutinesListAdapter

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