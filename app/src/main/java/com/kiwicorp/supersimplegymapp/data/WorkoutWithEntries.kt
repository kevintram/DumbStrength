package com.kiwicorp.supersimplegymapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithEntries (
    @Embedded
    val workout: Workout,
    @Relation(
        parentColumn = "workout_id",
        entityColumn = "entry_workout_creator_id",
        entity = Entry::class
    )
    val entries: List<EntryWithActivity>
) {
    val description: String
        get() {
            var result = ""
            for (entryWithActivity in entries) {
                result += "${entryWithActivity.activity.name}\n\t${entryWithActivity.entry.description}\n"
            }
            return result
        }
}