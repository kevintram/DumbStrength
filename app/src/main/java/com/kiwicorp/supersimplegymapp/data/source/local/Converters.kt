package com.kiwicorp.supersimplegymapp.data.source.local

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class Converters {
    @TypeConverter
    fun zonedDateTimeToString(zonedDateTime: ZonedDateTime): String = zonedDateTime.toString()

    @TypeConverter
    fun stringToZonedDateTime(string: String): ZonedDateTime = ZonedDateTime.parse(string)
}