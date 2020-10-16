package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiwicorp.supersimplegymapp.data.Entry
import com.kiwicorp.supersimplegymapp.data.EntryWithActivity

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries WHERE entry_activity_id = :activityId")
    fun observeEntriesByActivityId(activityId: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE entry_workout_creator_id = :workoutId")
    suspend fun getEntriesByWorkoutId(workoutId: String): List<Entry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: Entry)

    @Update
    suspend fun updateEntry(entry: Entry): Int

    @Delete
    suspend fun deleteEntry(entry: Entry): Int
}