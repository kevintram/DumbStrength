package com.kiwicorp.supersimplegymapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "routines")
data class Routine(
    @ColumnInfo(name = "routine_name")
    val name: String,

    @ColumnInfo(name = "routine_index")
    var index: Int,

    @PrimaryKey @ColumnInfo(name = "routine_id")
    val id: String = UUID.randomUUID().toString()
)