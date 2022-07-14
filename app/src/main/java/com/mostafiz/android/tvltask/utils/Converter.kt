package com.mostafiz.android.tvltask.utils
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Converter {
    @TypeConverter
    fun fromLongToDateString(value: Long?): String? {
        val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy HH:mm a", Locale.US)
        return if (value == null) null else dateFormat.format(Date(value))
    }

}