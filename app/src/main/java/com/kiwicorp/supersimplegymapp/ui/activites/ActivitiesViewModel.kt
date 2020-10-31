package com.kiwicorp.supersimplegymapp.ui.activites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActivitiesViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {
    private var searchQuery = ""

    var activities = MediatorLiveData<List<Activity>>().apply {
        addSource(activityRepository.activities) {
            viewModelScope.launch {
                value = activityRepository.getSearchActivities(searchQuery)
            }
        }
    }

    private var searchJob: Job? = null

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

    fun onSearchQueryChanged(query: String?) {
        val newQuery = query ?: ""
        if (newQuery != searchQuery) {
            searchQuery = newQuery
            executeSearch()
        }
    }

    private fun executeSearch() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            // The user could be typing rapidly. Giving the search job a slight delay and cancelling
            // it on each call to this method effectively debounces.
            delay(500)
            activities.value = activityRepository.getSearchActivities(searchQuery)
        }
    }
}