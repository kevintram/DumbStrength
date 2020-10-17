package com.kiwicorp.supersimplegymapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithEntries(
    @Embedded
    val routine: Routine,
    @Relation(
        parentColumn = "routine_id",
        entityColumn = "routine_entry_routine_creator_id"
    )
    val entries: List<RoutineEntry>
)