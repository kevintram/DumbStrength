package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "routine_entries",
    foreignKeys = [
        ForeignKey(
            entity = Activity::class,
            parentColumns = ["activity_id"],
            childColumns = ["routine_entry_activity_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Routine::class,
            parentColumns = ["routine_id"],
            childColumns = ["routine_entry_routine_creator_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoutineEntry(
    @ColumnInfo(name = "routine_entry_activity_id")
    val activityId: String,

    @ColumnInfo(name = "routine_entry_routine_creator_id")
    val routineId: String,

    @ColumnInfo(name = "routine_entry_index")
    var index: Int,

    @ColumnInfo(name = "routine_entry_description")
    var description: String,

    @PrimaryKey @ColumnInfo(name = "routine_entry_id")
    val id: String = UUID.randomUUID().toString()
)