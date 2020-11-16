package com.kiwicorp.supersimplegymapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithEntries (
    @Embedded
    val workout: Workout,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "workout_entry_workout_creator_id",
        entity = WorkoutEntry::class
    )
    var entries: List<WorkoutEntryWithActivity>
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