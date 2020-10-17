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
import java.util.*

class AddEditWorkoutViewModel @ViewModelInject constructor(
    private val workoutRepository: WorkoutRepository,
    activityRepository: ActivityRepository
): ViewModel() {
    //initialize workout id here it can be used for entries
    private var workoutId = UUID.randomUUID().toString()

    private val _date = MutableLiveData(LocalDate.now())
    val date: LiveData<LocalDate> = _date

    private val _entries = MutableLiveData(listOf<WorkoutEntryWithActivity>())
    val entries: LiveData<List<WorkoutEntryWithActivity>> = _entries

    val activities = activityRepository.activities

    private val _navigateToChooseActivityFragment = MutableLiveData<Event<Unit>>()
    val navigateToChooseActivityFragment: LiveData<Event<Unit>> = _navigateToChooseActivityFragment

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun loadWorkout(workoutId: String) {
        // stop if workout already loaded (needed because all changes will be undone when navigating
        // from ChooseActivityFragment to AddEditWorkoutFragment)
        if (workoutId == this.workoutId) {
            return
        }
        viewModelScope.launch {
            val workoutWithEntries = workoutRepository.getWorkout(workoutId)
            this@AddEditWorkoutViewModel.workoutId = workoutId
            _date.value = workoutWithEntries.workout.date
            _entries.value = workoutWithEntries.workoutEntries
        }
    }

    fun insertWorkoutAndClose() {
        viewModelScope.launch {
            val workout = Workout(date.value!!,workoutId)
            workoutRepository.insertWorkout(workout)

            for (entryWithActivity in entries.value!!) {
                workoutRepository.insertEntry(entryWithActivity.workoutEntry)
            }

            close()
        }
    }

    fun deleteWorkoutAndClose() {
        viewModelScope.launch {
            val workout = Workout(date.value!!, workoutId)
            workoutRepository.deleteWorkout(workout)
            // entries don't need to be deleted here because workout is a foreign key in Entry
            close()
        }
    }

    fun updateWorkoutAndClose() {
        viewModelScope.launch {
            val workout = Workout(date.value!!, workoutId)
            workoutRepository.updateWorkout(workout)

            val prevEntries = workoutRepository.getEntriesByWorkoutId(workoutId)

            val entriesToDelete = prevEntries.dropWhile {
                // drop if this entry is in entries
                for (entryWithActivity in entries.value!!) {
                    if (entryWithActivity.workoutEntry.id == it.id) {
                        return@dropWhile true
                    }
                }
                return@dropWhile false
            }

            for (entry in entriesToDelete) {
                workoutRepository.deleteEntry(entry)
            }

            for (entryWithActivity in entries.value!!) {
                // don't use update, use insert because onConflict = Replace. So if already exists
                // will be updated; if doesn't exist will be inserted.
                workoutRepository.insertEntry(entryWithActivity.workoutEntry)
            }

            close()
        }
    }

    fun chooseActivity(activity: Activity) {
        val entry = WorkoutEntry(activity.id,workoutId,"", date.value!!)
        val entryWithActivity = WorkoutEntryWithActivity(entry, activity)
        _entries.value = _entries.value!!.plusElement(entryWithActivity)
    }

    fun unchooseActivity(activity: Activity) {
        // drop entries with the same activity (for some reason dropWhile() will not work)
        _entries.value = _entries.value!!.filter {
            it.activity.id != activity.id
        }
    }

    fun navigateToChooseActivityFragment() {
        _navigateToChooseActivityFragment.value = Event(Unit)
    }

    fun close() {
        _close.value = Event(Unit)
    }

}