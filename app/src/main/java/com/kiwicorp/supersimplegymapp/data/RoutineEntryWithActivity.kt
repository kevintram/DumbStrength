package com.kiwicorp.supersimplegymapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineEntryWithActivity(
    @Embedded
    val routineEntry: RoutineEntry,
    @Relation(
        parentColumn = "routine_entry_activity_id",
        entityColumn = "activity_id"
    )
    val activity: Activity
)