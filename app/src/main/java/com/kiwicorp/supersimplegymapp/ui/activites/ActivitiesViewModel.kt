package com.kiwicorp.supersimplegymapp.ui.activites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import kotlinx.coroutines.launch

class ActivitiesViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {
    val activities = activityRepository.activities

    fun insert() {
        viewModelScope.launch {
            activityRepository.insertActivity(Activity("JUICE","I AM DESC"))
        }
    }
}