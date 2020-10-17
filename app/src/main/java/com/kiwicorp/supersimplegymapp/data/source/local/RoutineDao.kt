package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kiwicorp.supersimplegymapp.data.Routine
import com.kiwicorp.supersimplegymapp.data.RoutineWithEntries

@Dao
interface RoutineDao {
    @Transaction
    @Query("SELECT * FROM routines")
    fun observeRoutines(): LiveData<List<RoutineWithEntries>>

    @Transaction
    @Query("SELECT * FROM routines")
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