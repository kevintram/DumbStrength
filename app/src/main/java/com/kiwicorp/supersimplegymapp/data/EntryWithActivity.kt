package com.kiwicorp.supersimplegymapp.data

import androidx.room.Embedded
import androidx.room.Relation

data class EntryWithActivity (
    @Embedded
    val entry: Entry,
    @Relation(
        parentColumn = "entry_activity_id",
        entityColumn = "activity_id",
        entity = Activity::class
    )
    val activity: Activity
)