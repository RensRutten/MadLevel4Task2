package com.example.madlevel4task2.Converters

import androidx.room.TypeConverter
import java.util.*


class DateConverter {
    @TypeConverter
    fun fromTime(value: Long?): Date? {
        return value?.let { Date(it) }
    }

}