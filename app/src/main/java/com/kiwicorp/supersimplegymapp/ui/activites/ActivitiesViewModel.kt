package com.kiwicorp.supersimplegymapp.ui.activites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.supersimplegymapp.Event

class ActivitiesViewModel @ViewModelInject constructor(): ViewModel() {
    private val _navigateToAddActivityFragment = MutableLiveData<Event<Unit>>()
    val navigateToAddActivityFragment: LiveData<Event<Unit>> = _navigateToAddActivityFragment

    private val _navigateToActivityDetailFragment = MutableLiveData<Event<String>>()
    val navigateToActivityDetailFragment: LiveData<Event<String>> = _navigateToActivityDetailFragment

    fun navigateToAddActivityFragment() {
        _navigateToAddActivityFragment.value = Event(Unit)
    }

    fun navigateToActivityDetail(activityId: String) {
        _navigateToActivityDetailFragment.value = Event(activityId)
    }
}