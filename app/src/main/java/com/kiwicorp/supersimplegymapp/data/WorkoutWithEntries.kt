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
    val workoutEntries: List<WorkoutEntryWithActivity>
) {
    val description: String
        get() {
            var result = ""
            for (entryWithActivity in workoutEntries) {
                result += "${entryWithActivity.activity.name}\n\t${entryWithActivity.workoutEntry.description}\n"
            }
            return result
        }
}