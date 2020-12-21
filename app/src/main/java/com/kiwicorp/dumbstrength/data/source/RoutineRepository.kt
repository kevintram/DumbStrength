package com.kiwicorp.dumbstrength.data.source

import com.kiwicorp.dumbstrength.data.Routine
import com.kiwicorp.dumbstrength.data.RoutineEntry
import com.kiwicorp.dumbstrength.data.RoutineWithEntries
import com.kiwicorp.dumbstrength.data.source.local.RoutineDao
import com.kiwicorp.dumbstrength.data.source.local.RoutineEntryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoutineRepository @Inject constructor(
   private val routineDao: RoutineDao,
   private val routineEntryDao: RoutineEntryDao
) {
   val routines = routineDao.observeRoutines()

   suspend fun getRoutines(): List<RoutineWithEntries> {
      return withContext(Dispatchers.IO) {
         routineDao.getRoutines()
      }
   }

   suspend fun getRoutineWithEntriesById(routineId: String): RoutineWithEntries {
      return withContext(Dispatchers.IO) {
         routineDao.getRoutineWithEntriesById(routineId)
      }
   }

   suspend fun insertRoutine(routine: Routine) {
      withContext(Dispatchers.IO) {
         routineDao.insertRoutine(routine)
      }
   }

   suspend fun updateRoutine(routine: Routine): Int {
      return withContext(Dispatchers.IO + NonCancellable) {
         routineDao.updateRoutine(routine)
      }
   }

   suspend fun deleteRoutine(routine: Routine) {
      withContext(Dispatchers.IO) {
         routineDao.deleteRoutine(routine)

         val routines = routineDao.getRoutines()
         //update indices
         for (i in routines.indices) {
            val it = routines[i].routine
            it.index = i
            updateRoutine(it)
         }
      }
   }

   suspend fun getEntriesByRoutineId(routineId: String): List<RoutineEntry> {
      return withContext(Dispatchers.IO) {
         routineEntryDao.getEntriesByRoutineId(routineId)
      }
   }

   suspend fun insertEntry(routineEntry: RoutineEntry) {
      withContext(Dispatchers.IO) {
         routineEntryDao.insertEntry(routineEntry)
      }
   }

   suspend fun updateEntry(routineEntry: RoutineEntry): Int {
      return withContext(Dispatchers.IO) {
         routineEntryDao.updateEntry(routineEntry)
      }
   }

   suspend fun deleteEntry(routineEntry: RoutineEntry): Int {
      return withContext(Dispatchers.IO) {
         routineEntryDao.deleteEntry(routineEntry)
      }
   }
}