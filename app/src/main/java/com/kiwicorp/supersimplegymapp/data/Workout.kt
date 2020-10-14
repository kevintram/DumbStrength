package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "workouts")
data class Workout(
    @ColumnInfo(name = "workout_date")
    val date: LocalDate,

    @PrimaryKey @ColumnInfo(name = "workout_id")
    val id: String = UUID.randomUUID().toString()
) {
    val dateStr: String
        get() {
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, d MMM")
            return dateFormatter.format(date)
        }
}