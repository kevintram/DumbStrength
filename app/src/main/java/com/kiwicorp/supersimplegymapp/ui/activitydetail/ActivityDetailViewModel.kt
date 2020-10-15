package com.kiwicorp.supersimplegymapp.ui.activitydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.Entry
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import kotlinx.coroutines.launch

class ActivityDetailViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository,
    private val workoutRepository: WorkoutRepository
): ViewModel() {
    private val _activity = MediatorLiveData<Activity>()
    val activity: LiveData<Activity> = _activity

    private val _entries = MediatorLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>> = _entries

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