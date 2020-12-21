package com.kiwicorp.dumbstrength.data.source.local

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun dateToString(date: LocalDate): String = date.toString()

    @TypeConverter
    fun stringToDateTime(string: String): LocalDate = LocalDate.parse(string)
}