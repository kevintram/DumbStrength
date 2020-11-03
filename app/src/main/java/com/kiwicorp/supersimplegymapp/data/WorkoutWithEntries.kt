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
    var entries: List<WorkoutEntryWithActivity>
) {
    val description: SpannableStringBuilder
        get() {
            val result = SpannableStringBuilder()
            for (entryWithActivity in entries) {
                result.bold { appendln(entryWithActivity.activity.name) }
                if (entryWithActivity.workoutEntry.description != "") {
                    result.appendln(entryWithActivity.workoutEntry.description)
                }
            }
            if (result.isNotEmpty()) {
                result.delete(result.length - 1,result.length)
            }
            return result
        }
}