package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "activities")
data class Activity(
    @ColumnInfo(name = "activity_name")
    val name: String,

    @ColumnInfo(name = "activity_description")
    val description: String? = null,

    @PrimaryKey @ColumnInfo(name = "activity_id")
    val id: String = UUID.randomUUID().toString()
)