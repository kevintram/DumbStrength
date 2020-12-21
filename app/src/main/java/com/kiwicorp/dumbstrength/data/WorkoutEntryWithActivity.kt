package com.kiwicorp.dumbstrength.data

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutEntryWithActivity (
    @Embedded
    val workoutEntry: WorkoutEntry,
    @Relation(
        parentColumn = "workout_entry_activity_id",
        entityColumn = "activity_id",
        entity = Activity::class
    )
    val activity: Activity
)