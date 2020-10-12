package com.kiwicorp.supersimplegymapp.ui.addeditactivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.Activity
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import kotlinx.coroutines.launch

class AddEditActivityViewModel @ViewModelInject constructor(
    private val activityRepository: ActivityRepository
): ViewModel() {

    val activityId: String? = null

    val name = MutableLiveData("")

    val description = MutableLiveData<String>()

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun insert() {
        viewModelScope.launch {
            val activity = Activity(name.value!!, description.value)
            activityRepository.insertActivity(activity)
            _close.value = Event(Unit)
        }
    }

    fun update() {
        viewModelScope.launch {
            val activity = Activity(name.value!!, description.value, activityId!!)
            activityRepository
        }
    }

    fun addDescription() {
        description.value = ""
    }
}