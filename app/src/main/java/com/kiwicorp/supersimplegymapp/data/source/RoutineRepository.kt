package com.kiwicorp.supersimplegymapp.data.source

import com.kiwicorp.supersimplegymapp.data.Routine
import com.kiwicorp.supersimplegymapp.data.RoutineEntry
import com.kiwicorp.supersimplegymapp.data.RoutineWithEntries
import com.kiwicorp.supersimplegymapp.data.source.local.RoutineDao
import com.kiwicorp.supersimplegymapp.data.source.local.RoutineEntryDao
import kotlinx.coroutines.Dispatchers
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

   suspend fun insertRoutine(routine: Routine) {
      withContext(Dispatchers.IO) {
         routineDao.insertRoutine(routine)
      }
   }

   suspend fun updateRoutine(routine: Routine): Int {
      return withContext(Dispatchers.IO) {
         routineDao.updateRoutine(routine)
      }
   }

   suspend fun deleteRoutine(routine: Routine): Int {
      return withContext(Dispatchers.IO) {
         routineDao.deleteRoutine(routine)
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