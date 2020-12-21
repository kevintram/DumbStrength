package com.kiwicorp.dumbstrength.ui.activitydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.dumbstrength.Event
import com.kiwicorp.dumbstrength.data.Activity
import com.kiwicorp.dumbstrength.data.WorkoutEntry
import com.kiwicorp.dumbstrength.data.source.ActivityRepository
import com.kiwicorp.dumbstrength.data.source.WorkoutRepository
import kotlinx.coroutines.launch

class ActivityDetailViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository,
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    private val _activity = MediatorLiveData<Activity>()
    val activity: LiveData<Activity> = _activity

    private val _entries = MediatorLiveData<List<WorkoutEntry>>()
    val entries: LiveData<List<WorkoutEntry>> = _entries

    private val _navigateToEditActivityFragment = MutableLiveData<Event<String>>()
    val navigateToEditActivityFragment: LiveData<Event<String>> = _navigateToEditActivityFragment

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun loadActivity(activityId: String) {
        _activity.addSource(activityRepository.observeActivity(activityId)) { _activity.value = it }

        _entries.addSource(workoutRepository.observeEntriesByActivityId(activityId)) {
            _entries.value = it
        }
    }
    
    fun deleteWorkoutAndClose() {
        viewModelScope.launch {
            activityRepository.deleteActivity(activity.value!!)
            close()
        }
    }

    fun navigateToEditActivityFragment() {
        _navigateToEditActivityFragment.value = Event(activity.value!!.id)
    }

    fun close() {
        _close.value = Event(Unit)
    }
}