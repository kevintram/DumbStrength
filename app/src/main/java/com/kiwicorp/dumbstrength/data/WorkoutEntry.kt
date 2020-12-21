package com.kiwicorp.dumbstrength.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(
    tableName = "workout_entries",
    foreignKeys = [
        ForeignKey(
            entity = Activity::class,
            parentColumns = ["activity_id"],
            childColumns = ["workout_entry_activity_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workout_id"],
            childColumns = ["workout_entry_workout_creator_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutEntry(
    @ColumnInfo(name = "workout_entry_activity_id")
    val activityId: String,

    @ColumnInfo(name = "workout_entry_workout_creator_id")
    val workoutId: String,

    @ColumnInfo(name = "workout_entry_index")
    var index: Int,

    @ColumnInfo(name = "workout_entry_description")
    var description: String,

    @ColumnInfo(name = "workout_entry_date")
    var date: LocalDate,

    @PrimaryKey @ColumnInfo(name = "workout_entry_id")
    val id: String = UUID.randomUUID().toString()
) {
    val dateStr: String
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM")
            return dateFormatter.format(date)
        }
}