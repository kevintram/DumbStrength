package com.kiwicorp.dumbstrength.ui.addeditactivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.dumbstrength.Event
import com.kiwicorp.dumbstrength.data.Activity
import com.kiwicorp.dumbstrength.data.source.ActivityRepository
import kotlinx.coroutines.launch

class AddEditActivityViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {

    var activityId: String? = null

    val name = MutableLiveData("")

    val description = MutableLiveData<String>()

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun loadActivity(activityId: String) {
        viewModelScope.launch {
            val activity = activityRepository.getActivity(activityId)
            this@AddEditActivityViewModel.activityId = activity.id
            name.value = activity.name
            description.value = activity.description
        }
    }

    fun insertWorkoutAndClose() {
        viewModelScope.launch {
            if (description.value == "") {
                description.value = null
            }
            val activity = Activity(name.value!!, description.value)
            activityRepository.insertActivity(activity)
            close()
        }
    }

    fun updateWorkoutAndClose() {
        viewModelScope.launch {
            if (description.value == "") {
                description.value = null
            }
            val activity = Activity(name.value!!, description.value, activityId!!)
            activityRepository.updateActivity(activity)
            close()
        }
    }

    fun addDescription() {
        description.value = ""
    }

    fun close() {
        _close.value = Event(Unit)
    }
}