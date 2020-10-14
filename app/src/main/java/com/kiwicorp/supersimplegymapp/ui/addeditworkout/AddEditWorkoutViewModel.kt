package com.kiwicorp.supersimplegymapp.ui.addeditworkout

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.*
import com.kiwicorp.supersimplegymapp.data.source.ActivityRepository
import com.kiwicorp.supersimplegymapp.data.source.WorkoutRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

class AddEditWorkoutViewModel @ViewModelInject constructor(
    private val workoutRepository: WorkoutRepository,
    activityRepository: ActivityRepository
): ViewModel() {
    //initialize workout id here it can be used for entries
    private val workoutId = UUID.randomUUID().toString()

    private val _entries = MutableLiveData(listOf<EntryWithActivity>())
    val entries: LiveData<List<EntryWithActivity>> = _entries

    val activities = activityRepository.activities

    private val _navigateToChooseActivityFragment = MutableLiveData<Event<Unit>>()
    val navigateToChooseActivityFragment: LiveData<Event<Unit>> = _navigateToChooseActivityFragment

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun chooseActivity(activity: Activity) {
        val entry = Entry(activity.id,workoutId,"", LocalDate.now())
        val entryWithActivity = EntryWithActivity(entry, activity)
        _entries.value = _entries.value!!.plusElement(entryWithActivity)
    }

    fun unchooseActivity(activity: Activity) {
        _entries.value = _entries.value!!.filter {
            it.activity.id != activity.id
        }
        println()
    }

    fun insertWorkoutAndClose() {
        viewModelScope.launch {
            val workout = Workout(LocalDate.now(),workoutId)
            workoutRepository.insertWorkout(workout)

            for (entryWithActivity in entries.value!!) {
                workoutRepository.insertEntry(entryWithActivity.entry)
            }

            close()
        }
    }

    fun navigateToChooseActivityFragment() {
        _navigateToChooseActivityFragment.value = Event(Unit)
    }

    fun close() {
        _close.value = Event(Unit)
    }

}