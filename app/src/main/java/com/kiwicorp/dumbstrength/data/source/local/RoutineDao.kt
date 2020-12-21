package com.kiwicorp.dumbstrength.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiwicorp.dumbstrength.data.Routine
import com.kiwicorp.dumbstrength.data.RoutineWithEntries

@Dao
interface RoutineDao {
    @Transaction
    @Query("SELECT * FROM routines ORDER BY routine_index")
    fun observeRoutines(): LiveData<List<RoutineWithEntries>>

    @Transaction
    @Query("SELECT * FROM routines ORDER BY routine_index")
    suspend fun getRoutines(): List<RoutineWithEntries>

    @Query("SELECT * FROM routines WHERE routine_id = :routineId")
    suspend fun getRoutineWithEntriesById(routineId: String): RoutineWithEntries

    @Insert
    suspend fun insertRoutine(routine: Routine)

    @Update
    suspend fun updateRoutine(routine: Routine): Int

    @Delete
    suspend fun deleteRoutine(routine: Routine): Int
}