package com.kiwicorp.supersimplegymapp.ui.activites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import kotlinx.coroutines.launch

class ActivitiesViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {
    val activities = activityRepository.activities

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