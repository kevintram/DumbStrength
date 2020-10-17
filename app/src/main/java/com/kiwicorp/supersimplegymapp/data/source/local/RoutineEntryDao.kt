package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.room.*
import com.kiwicorp.supersimplegymapp.data.RoutineEntry

@Dao
interface RoutineEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(routineEntry: RoutineEntry)

    @Update
    suspend fun updateEntry(routineEntry: RoutineEntry): Int

    @Delete
    suspend fun deleteEntry(routineEntry: RoutineEntry): Int
}