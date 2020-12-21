package com.kiwicorp.dumbstrength.ui.chooseactivitycommon

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.dumbstrength.data.Activity
import com.kiwicorp.dumbstrength.data.source.ActivityRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivityViewModel @ViewModelInject constructor(
    val activityRepository: ActivityRepository
) : ViewModel() {
    private var searchQuery = ""

    var activities = MediatorLiveData<List<Activity>>().apply {
        addSource(activityRepository.activities) {
            viewModelScope.launch {
                value = activityRepository.getSearchActivities(searchQuery)
            }
        }
    }

    private var searchJob: Job? = null

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
