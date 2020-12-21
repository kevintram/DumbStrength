package com.kiwicorp.dumbstrength.data.source.local

import androidx.room.*
import com.kiwicorp.dumbstrength.data.RoutineEntry

@Dao
interface RoutineEntryDao {
    @Query("SELECT * FROM routine_entries WHERE routine_entry_routine_creator_id = :routineId")
    suspend fun getEntriesByRoutineId(routineId: String): List<RoutineEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(routineEntry: RoutineEntry)

    @Update
    suspend fun updateEntry(routineEntry: RoutineEntry): Int

    @Delete
    suspend fun deleteEntry(routineEntry: RoutineEntry): Int
}