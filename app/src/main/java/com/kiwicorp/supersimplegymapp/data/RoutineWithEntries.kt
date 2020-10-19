package com.kiwicorp.supersimplegymapp.data

import android.text.SpannableStringBuilder
import androidx.core.text.bold
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
    val description: SpannableStringBuilder
        get() {
            val result = SpannableStringBuilder()
            for (entryWithActivity in entries) {
                result.bold { appendln(entryWithActivity.activity.name) }
                    .appendln(entryWithActivity.routineEntry.description)

            }
            return result
        }
}