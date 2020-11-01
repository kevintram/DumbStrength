package com.kiwicorp.supersimplegymapp.ui.addeditroutine

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwicorp.supersimplegymapp.Event
import com.kiwicorp.supersimplegymapp.data.*
import com.kiwicorp.supersimplegymapp.data.source.RoutineRepository
import com.kiwicorp.supersimplegymapp.ui.chooseactivitycommon.ChooseActivityListAdapter
import kotlinx.coroutines.launch
import java.util.*

class AddEditRoutineViewModel @ViewModelInject constructor(
    private val routineRepository: RoutineRepository
): ViewModel(), ChooseActivityListAdapter.ChooseActivityActions {
    //initialize routine id here it can be used for entries
    private var routineId = UUID.randomUUID().toString()

    private val _entries = MutableLiveData(listOf<RoutineEntryWithActivity>())
    val entries: LiveData<List<RoutineEntryWithActivity>> = _entries

    val name = MutableLiveData("Routine #${routineRepository.routines.value?.size?.plus(1) ?: 1}")

    private val _navigateToChooseActivityFragment = MutableLiveData<Event<Unit>>()
    val navigateToChooseActivityFragment: LiveData<Event<Unit>> = _navigateToChooseActivityFragment

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>> = _close

    fun loadRoutine(routineId: String) {
        if (routineId == this.routineId) {
            return
        }
        viewModelScope.launch {
            val routineWithEntries = routineRepository.getRoutineWithEntriesById(routineId)
            name.value = routineWithEntries.routine.name
            this@AddEditRoutineViewModel.routineId = routineId
            _entries.value = routineWithEntries.entries
        }
    }

    fun insertRoutineAndClose() {
        viewModelScope.launch {
            val routine = Routine(name.value!!, routineId)
            routineRepository.insertRoutine(routine)

            for (entryWithActivity in entries.value!!) {
                routineRepository.insertEntry(entryWithActivity.routineEntry)
            }

            close()
        }
    }

    fun deleteRoutineAndClose() {
        viewModelScope.launch {
            val routine = Routine(name.value!!, routineId)
            routineRepository.deleteRoutine(routine)
            // entries don't need to be deleted here because workout is a foreign key in RoutineEntry
            close()
        }
    }

    fun updateRoutineAndClose() {
        viewModelScope.launch {
            val routine = Routine(name.value!!,routineId)
            routineRepository.updateRoutine(routine)

            val prevEntries = routineRepository.getEntriesByRoutineId(routineId)

            val entriesToDelete = prevEntries.dropWhile {
                // drop if this entry is in entries
                for (entryWithActivity in entries.value!!) {
                    if (entryWithActivity.routineEntry.id == it.id) {
                        return@dropWhile true
                    }
                }
                return@dropWhile false
            }

            for (entry in entriesToDelete) {
                routineRepository.deleteEntry(entry)
            }

            for (entryWithActivity in entries.value!!) {
                // don't use update, use insert because onConflict = Replace. So if already exists
                // will be updated; if doesn't exist will be inserted.
                routineRepository.insertEntry(entryWithActivity.routineEntry)
            }

            close()
        }
    }

    override fun chooseActivity(activity: Activity) {
        val entry = RoutineEntry(activity.id,routineId,"")
        val entryWithActivity = RoutineEntryWithActivity(entry,activity)
        _entries.value = _entries.value!!.plusElement(entryWithActivity)
    }

    override fun unchooseActivity(activity: Activity) {
        // drop entries with the same activity (for some reason dropWhile() will not work)
        _entries.value = _entries.value!!.filter {
            it.activity.id != activity.id
        }
    }

    override fun activityIsInEntries(activity: Activity): Boolean {
        for (entryWithActivity in entries.value!!) {
            if (entryWithActivity.activity == activity) {
                return true
            }
        }
        return false
    }

    fun navigateToChooseActivityFragment() {
        _navigateToChooseActivityFragment.value = Event(Unit)
    }

    fun close() {
        _close.value = Event(Unit)
    }

}