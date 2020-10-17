package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_entries")
data class RoutineEntry(
    @ColumnInfo(name = "routine_entry_activity_id")
    val activityId: String,

    @ColumnInfo(name = "routine_entry_routine_creator_id")
    val routineId: String,

    @ColumnInfo(name = "routine_entry_description")
    val description: String,

    @PrimaryKey @ColumnInfo(name = "routine_entry_id")
    val id: String
)