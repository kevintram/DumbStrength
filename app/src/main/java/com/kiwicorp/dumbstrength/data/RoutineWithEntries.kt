package com.kiwicorp.dumbstrength.data

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithEntries(
    @Embedded
    val routine: Routine,
    @Relation(
        parentColumn = "routine_id",
        entityColumn = "routine_entry_routine_creator_id",
        entity = RoutineEntry::class
    )
    val entries: List<RoutineEntryWithActivity>
) {
    val description: String
        get() {
            var result = ""
            for (entryWithActivity in entries) {
                result += "${entryWithActivity.activity.name}\n"
            }
            return if (result.isNotEmpty()) result.dropLast(1) else result
        }
}