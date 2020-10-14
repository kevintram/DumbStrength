package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(
    tableName = "entries",
    foreignKeys = [
        ForeignKey(
            entity = Activity::class,
            parentColumns = ["activity_id"],
            childColumns = ["entry_activity_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workout_id"],
            childColumns = ["entry_workout_creator_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Entry(
    @ColumnInfo(name = "entry_activity_id")
    val activityId: String,

    @ColumnInfo(name = "entry_workout_creator_id")
    val workoutId: String,

    @ColumnInfo(name = "entry_description")
    var description: String,

    @ColumnInfo(name = "entry_date")
    var date: LocalDate,

    @PrimaryKey @ColumnInfo(name = "entry_id")
    val id: String = UUID.randomUUID().toString()
) {
    val dateStr: String
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM")
            return dateFormatter.format(date)
        }
}