package com.kiwicorp.supersimplegymapp.data

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.core.text.inSpans
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
    val entries: List<WorkoutEntryWithActivity>
) {
    val description: SpannableStringBuilder
        get() {
            val result = SpannableStringBuilder()
            for (entryWithActivity in entries) {
                result.bold { appendln(entryWithActivity.activity.name) }
                    .appendln(entryWithActivity.workoutEntry.description)
            }
            return result
        }
}